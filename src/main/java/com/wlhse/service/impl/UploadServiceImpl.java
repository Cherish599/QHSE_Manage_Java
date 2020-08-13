package com.wlhse.service.impl;

import com.wlhse.dao.CheckListDao;
import com.wlhse.dao.FileDao;
import com.wlhse.dao.QHSEManageSysElementsDao;
import com.wlhse.dto.*;
import com.wlhse.dto.inDto.FilePropagationFileInfo;
import com.wlhse.dto.inDto.QSHEMSElementInDto;
import com.wlhse.exception.WLHSException;
import com.wlhse.service.UploadService;
import com.wlhse.util.*;
import com.wlhse.util.state_code.CodeDict;
import com.wlhse.util.state_code.NR;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class UploadServiceImpl implements UploadService {



    @Resource
    private PoiUtil poiUtil;

    @Resource
    private GetBeanListByExcel getBeanListByExcel;

    @Resource
    private CheckListDao checkListDao;
    @Resource
    private QHSEManageSysElementsDao qHSEManageSysElementsDao;

    private final static String staus = "启用";

    @Resource
    FileDao fileDao;

    @Override
    @Async
    public String uploadEmployees(String path) throws Exception {
//        String[] strArray = {"companyCode","employeeCode", "name", "sex", "birthday", "jobTime",
//                "empGroup","education","email","tel","category","uName","roleCode"};
        String[] strArray = {"companyCode","companyName","name", "empGroup","category","uName","roleCode"};
        Workbook workbook = poiUtil.createWorkbook(path);
        return getBeanListByExcel.getBeanList(workbook, strArray, EmployeeExcelDto.class);
    }

    @Override
    public String uploadReports(String path) throws Exception {
        String[] strArray = {"companyCode","companyName","reportCode", "reportType","reportPlanDate","reportCheckPersonID"
                ,"reportCheckPersonName","auditorIDs","auditorNames","auditorDate","approverIDs","approverNames","approverDate","fileDate","senderID"
                ,"senderName","sendDate","reportCount","seal1","seal2","seal3","seal4","seal5","seal6","note","sealPersonID"
                ,"sealPersonName","authID","authName","sealDate","sampleName","sampleNO","sampleModel","sampleCode"
                ,"entrustCompany","productCompany","customerCompany","arriveDate","checkDate","checkAddress","checkProject"
                ,"checkGuist","checkResult","sampleCheckPersonName"};
        Workbook workbook = poiUtil.createWorkbook(path);
        return getBeanListByExcel.getReportsBeanList(workbook, strArray, ExcelUploadReportDto.class);
    }

    /**
     * 该方法用于数据库新增checkList数据
     * @param path 传入文件的路径
     * @return 返回操作成功，失败，重复编码，excel为空等消息
     * @throws Exception
     */
    @Override
    public R uploadCheckList(String path) throws Exception {
        //String[] strArray = {"checkListCode","checkListName","attribute","parentName","isChildNode","status", "checkContent"};
        Workbook workbook = poiUtil.createWorkbook(path);
        Sheet sheet = workbook.getSheetAt(0);//获取指定表可以改成自动获取
        //获取EXCEL中CheckList的值
        List<CheckListDto> beanList = new ArrayList<>();
        DataFormatter dataFormat=new DataFormatter();
       // System.out.println(sheet.getPhysicalNumberOfRows());
        for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {//从第二行读
            HashMap<String, String> checkListValueMap = new HashMap<>();
            Row row = sheet.getRow(j);//按行取
            checkListValueMap.put("checkListCode",dataFormat.formatCellValue(row.getCell(0)));
            //System.out.println(dataFormat.formatCellValue(row.getCell(0)));
            checkListValueMap.put("checkListName",dataFormat.formatCellValue(row.getCell(1)));
            checkListValueMap.put("attribute",dataFormat.formatCellValue(row.getCell(2)));
            checkListValueMap.put("parentName",dataFormat.formatCellValue(row.getCell(3)));
            checkListValueMap.put("isChildNode",dataFormat.formatCellValue(row.getCell(4)));
            checkListValueMap.put("status",dataFormat.formatCellValue(row.getCell(5)));
            //使用BeanUtils将封装的属性注入对象
            CheckListDto checkListDto=new CheckListDto();
            BeanUtils.populate(checkListDto, checkListValueMap);
            beanList.add(checkListDto);
        }
        workbook.close();
        if (beanList.size() > 0) {
            String duplicCode=PoiMSElement.isDuplicelements2(beanList);//判断是否有重复编码
            if (duplicCode== null) {
                //优化，一次把所有code查询出来放进list，在list中查找code
                List<String> list=checkListDao.querryAllCheckListCode();
                for(CheckListDto ele:beanList) {
                    if(list.contains(ele.getCheckListCode())) {//-------编码存在则更新
                        if (checkListDao.updateCheckListByCode(ele) <= 0)
                            throw new WLHSException("更新失败");
                    }
                    else{//--------不存在则插入
                        if (checkListDao.addCheckList(ele) <= 0)
                            throw new WLHSException("新增失败");
                    }
                }
                return R.ok("文件上传成功");//导入数据库成功
            }
            else {
                throw new WLHSException("有重复编码"+duplicCode);//提示有重复编码
            }
        }
        else {
            throw new WLHSException("excel文件为空");//list为空，读取excel失败；
        }
    }
    /**
     * 该方法用于管理要素审核excel录入数据库
     * @param path 传入文件的路径
     * @return 返回操作成功，失败，重复编码，excel为空等消息
     * @throws Exception
     */

    @Override
    public R uploadQHSEManageSysElements(String path) throws Exception {
        /*
        思想：创建excel工具类对象，使用该对象对表格进行读写，读写的顺序为一行一行从左往右，每读一行，即一条记录，一个对象；
        然后把存放对象属性值的键值对MAP封装为对象，放进list中；
        然后对数据进行校验，包括是否为空表，读取失败，有重复编码；都没问题再写入，根据code有则更新，无则添加；
         */
        //创建一个字段数组，用于放入对象的map，一定要对应excel里的列顺序
        String[] fieldArray = {
                "code",//编码
                "name",//名字
                "content",//内容
                "auditMode",//审核方式
                "initialScore",//分数
                "formula", //计算公式
                "problemDescription",//问题描述，插入另一个数据库
                "totalCount",//第五级叶子总数
                "status"//状态
        };
        Workbook workbook = poiUtil.createWorkbook(path);
        //得到第一张表
        Sheet sheet = workbook.getSheetAt(0);
        // 得到标题行
        // Row titleRow=sheet.getRow(0);
        //创建实体类对象容器,放入审核要素对象
        List<QSHEMSElementInDto> beanList = new ArrayList<>();
        //创建创建MAP,放入问题描述对象
        Map<String, String> problemDescriptionMap = new HashMap<>();
        //获取EXCEL中的值
        DataFormatter dataFormat=new DataFormatter();
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {//类似二维数组的读取，外层为行，内层为列；从第2行开始读；
            HashMap<String, String> QSHEMSElementValueMap = new HashMap<>();
            Row row = sheet.getRow(i);
            String rcode=new String();
            for(int j=0;j<fieldArray.length;j++)//j可以j<titleRow.getLastCellNum()，但害怕excel错误添加,有多余的字段
            {
                String value=dataFormat.formatCellValue(row.getCell(j));
                if(j==0){//找到该条记录的code
                    rcode=value;
                    if(rcode==null||"".equals(value)||" ".equals(value))//检查code是否为空
                        throw new WLHSException("存在code为空或有空行");
                }
                if("problemDescription".equals(fieldArray[j])) {//问题描述，先放进map，等所有格式检查无误后，再统一插入；
                    if(value==null||"".equals(value)||" ".equals(value)||"  ".equals(value))//如果不是叶子节点，就为空，直接跳过
                        continue;
                    else {
                        if(value.startsWith("1")){//检查是不是以序号1开头
                            problemDescriptionMap.put(rcode,value);
                            continue;
                        }
                        else throw new WLHSException("编号不是1开始");
                    }
                }
                else {//当不为问题描述时，直接将属性键值对放入map
                    QSHEMSElementValueMap.put(fieldArray[j], value);//读取第i行第j列；
                }
            }
            /*QSHEMSElementValueMap.put("code", dataFormat.formatCellValue(row.getCell(0)));*/
            //使用BeanUtils将封装的属性注入对象
            QSHEMSElementInDto qSHEMSElement=new QSHEMSElementInDto();
            BeanUtils.populate(qSHEMSElement, QSHEMSElementValueMap);
            //对象放进进容器
            beanList.add(qSHEMSElement);
        }
        workbook.close();
        if (beanList.size() > 0)//开始检查装入容器是否成功
        {
            String duplicCode=PoiMSElement.isDuplicelements(beanList);//判断是否有重复编码
            if (duplicCode== null)//至此，所有格式检查完毕，开始导入
            { //先导入审核要素表
             //优化，一次把所有code查询出来放进list，在list中查找code
                List<String> list=qHSEManageSysElementsDao.queryAllCode();
                for(QSHEMSElementInDto ele:beanList) {
                    if(list.contains(ele.getCode())) {//-------编码存在则更新
                        if (qHSEManageSysElementsDao.updateExcelElement(ele) <= 0)
                            throw new WLHSException("更新失败");
                    }
                    else {//--------不存在则插入
                        if (qHSEManageSysElementsDao.addExcelQHSEElement(ele) <= 0)
                            throw new WLHSException("新增失败");
                    }
                }
                //再导入问题描述表
                insertProblemDescription(problemDescriptionMap);
                return R.ok("文件上传成功");//导入数据库成功
            }
            else throw new WLHSException("有重复编码："+duplicCode);//提示有重复编码
        }
        else throw new WLHSException("excel文件为空");//list为空，读取excel失败；
    }

    @Override
    public boolean insertFilePropagationFileRecord(FilePropagationFileInfo filePropagationFileInfo) {
        int i = fileDao.insertFilePropagationFile(filePropagationFileInfo);
        if (i!=0) return true;
        return false;
    }

    /**
     * 该方法用于问题描述的插入
     * @param problemDescription Map<String, String>型，存放的是问题描述的code,problemDescription键值对；
     */
    @Transactional
    public void insertProblemDescription(Map<String, String> problemDescription)  {
        /*
        思想：算法升级，根据递增序号1，2，3，4....打断，能有效解决：1.占总数的1.5% 2注安占专职人员的比例等于20% 3注安占专职人员的比例在20%以下等格式
        但弊端是，序号必须是递增的，1，2，2，3就会打断失败。
         */
        String code;
        String description;
        //为了提高效率，直接把数据表先清空，然后再插入。使用事务管理防止插入失败造成数据丢失。
        qHSEManageSysElementsDao.deleteAllDescription();
        for(Map.Entry<String,String> entry: problemDescription.entrySet())
        {
            code=entry.getKey();
            description=entry.getValue();
            String[] s=description.split("1",2);
            for(int i=2;s[1].contains(String.valueOf(i));i++) {
                description=s[1];
                s=description.split(String.valueOf(i),2);
                //得到的s[0]即为插入的问题描述；
                //有".",就去除，没有”."就直接写入
                if(qHSEManageSysElementsDao.addProblemDescription(code,(s[0].startsWith(".")? s[0].substring(1):s[0]))<=0)
                    throw new WLHSException("新增失败");
            }//插入最后个问题描述，有".",就去除，没有”."就直接写入
            if(qHSEManageSysElementsDao.addProblemDescription(code, (s[1].startsWith(".")? s[1].substring(1):s[1]))<=0)
                throw new WLHSException("新增失败");
        }

        //下列方法以数字打断，能打断序号为88...2...3...56...格式，但不能打断内容中有数字的
        // String[] description=problemDescription.split("([1-9][0-9]{0,1})");//用0-99的数字打断，中间为正则表达式
        /*for(int i=0;i<description.length;i++)
        {
            if(i!=0) {//数组第一个必为空字符“ ”；
                if (".".equals(description[i].substring(0, 1))){//数据格式为：1.问题某某（有“.”符号）
                    if(qHSEManageSysElementsDao.addProblemDescription(code, description[i].substring(1))<=0)
                        throw new WLHSException("新增失败");
                }
                else{//数据格式为：1问题某某（无“.”符号），增加兼容性。
                    if(qHSEManageSysElementsDao.addProblemDescription(code, description[i])<=0)
                        throw new WLHSException("新增失败");
                }
            }
        }*/
    }
}
