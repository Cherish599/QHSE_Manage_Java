package com.wlhse.service.impl;

import com.wlhse.dao.CheckListDao;
import com.wlhse.dao.QHSEManageSysElementsDao;
import com.wlhse.dto.*;
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

//用于数据库新增checkList数据
    @Override
    public String uploadCheckList(String path) throws Exception {
        //String[] strArray = {"checkListCode","checkListName","attribute","parentName","isChildNode","status", "checkContent"};
        Workbook workbook = poiUtil.createWorkbook(path);
        Sheet sheet = workbook.getSheetAt(2);//获取指定表可以改成自动获取
        //获取EXCEL中CheckList的值
        CheckListDto checkListDto=new CheckListDto();
        HashMap<String, String> checkListValueMap = new HashMap<>();
        DataFormatter dataFormat=new DataFormatter();
        System.out.println(sheet.getPhysicalNumberOfRows());
        for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
            Row row = sheet.getRow(j);//按行取
            checkListValueMap.put("checkListCode",dataFormat.formatCellValue(row.getCell(0)));
            checkListValueMap.put("checkListName",dataFormat.formatCellValue(row.getCell(1)));
            checkListValueMap.put("attribute",dataFormat.formatCellValue(row.getCell(2)));
            checkListValueMap.put("parentName",dataFormat.formatCellValue(row.getCell(3)));
            checkListValueMap.put("isChildNode",dataFormat.formatCellValue(row.getCell(4)));
            checkListValueMap.put("status",dataFormat.formatCellValue(row.getCell(5)));
            checkListValueMap.put("checkContent",dataFormat.formatCellValue(row.getCell(6)));
            //使用BeanUtils将封装的属性注入对象
            BeanUtils.populate(checkListDto, checkListValueMap);
            String id=checkListDao.checkListIsExist(checkListDto);
            if(id==null||"".equals(id)){//插入checklist
                System.out.println("新加入");
                checkListDao.addCheckList(checkListDto);
            }else{
                checkListDto.setCheckListID(Integer.valueOf(id));
            }
        }
        return null;
    }

    //管理要素审核excel录入数据库
    @Override
    public String uploadQHSEManageSysElements(String path) throws Exception {
        Workbook workbook = poiUtil.createWorkbook(path);
        //得到第一张表
        Sheet sheet = workbook.getSheetAt(0);
        // 得到标题行
        Row titleRow=sheet.getRow(0);
        //创建实体类对象容器
        List<QSHEMSElementInDto> beanList = new ArrayList<>();
        //获取EXCEL中的值DataFormatter dataFormat=new DataFormatter();
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            HashMap<String, String> QSHEMSElementValueMap = new HashMap<>();
            Row row = sheet.getRow(i);//按行读取
            String rcode=new String();
            for(int j=0;j<titleRow.getLastCellNum();j++)
            {
                // 得到列名
                String key = titleRow.getCell(j).getStringCellValue();
                //得到当前列的值
                String value=dataFormat.formatCellValue(row.getCell(j));
                //找到该条记录的code
                if("code".equals(key)) {
                    rcode = value;
                }
                if("problemDescription".equals(key)) {
                    if(value==null||"".equals(value))
                        continue;
                    else {
                        insertProblemDescription(rcode, value);
                        continue;
                    }
                }
                QSHEMSElementValueMap.put(key, value);
            }
            /*QSHEMSElementValueMap.put("code", dataFormat.formatCellValue(row.getCell(0)));
            QSHEMSElementValueMap.put("name", dataFormat.formatCellValue(row.getCell(1)));
            QSHEMSElementValueMap.put("content", dataFormat.formatCellValue(row.getCell(2)));
            QSHEMSElementValueMap.put("basis", dataFormat.formatCellValue(row.getCell(3)));
            QSHEMSElementValueMap.put("auditMode", dataFormat.formatCellValue(row.getCell(4)));
            QSHEMSElementValueMap.put("initialScore", dataFormat.formatCellValue(row.getCell(5)));
            QSHEMSElementValueMap.put("formula", dataFormat.formatCellValue(row.getCell(6)));
            QSHEMSElementValueMap.put("problemDescription", dataFormat.formatCellValue(row.getCell(7)));
            QSHEMSElementValueMap.put("totalCount", dataFormat.formatCellValue(row.getCell(8)));
            QSHEMSElementValueMap.put("status", dataFormat.formatCellValue(row.getCell(9)));*/
            //使用BeanUtils将封装的属性注入对象
            QSHEMSElementInDto qSHEMSElement=new QSHEMSElementInDto();
            BeanUtils.populate(qSHEMSElement, QSHEMSElementValueMap);
            //放进进容器
            beanList.add(qSHEMSElement);
        }
        workbook.close();
        if (beanList.size() > 0)
        {
            String duplicCode=PoiMSElement.isDuplicelements(beanList);//判断是否有重复编码
            if (duplicCode== null)
            {
                for(QSHEMSElementInDto ele:beanList) {
                    String ecode = qHSEManageSysElementsDao.querryCode(ele.getCode());
                    if(ecode == null||"".equals(ecode)) //--------不存在则插入
                    {
                        if (qHSEManageSysElementsDao.addExcelQHSEElement(ele) <= 0)
                            throw new WLHSException("新增失败");
                    }
                    else//-------编码存在则更新-
                    {
                        if (qHSEManageSysElementsDao.updateExcelElement(ele) <= 0)
                            throw new WLHSException("更新失败");
                    }
                }
                return NR.getPoiProblemReturn(CodeDict.SUCCESS, 0);//导入数据库成功
            }
            else {
                return NR.getPoiReportsReturn(CodeDict.POI_ReportCodeDuplic_ERROR, duplicCode);//提示有重复编码
            }
        }
        else {
            return NR.getPoiProblemReturn(CodeDict.POI_PROBLEM_EMPTY_FIRST, 0);//list为空，读取excel失败；
        }
    }
    public void insertProblemDescription(String code,String problemDescription)  {
        String[] description=problemDescription.split("([1-9][0-9]{0,1})");//用0-99的数字打断，中间为正则表达式
        qHSEManageSysElementsDao.deleteByCode(code);//先把该code的问题描述全部删除，再添加。
        for(int i=0;i<description.length;i++)
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
        }
    }
}
