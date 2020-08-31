package com.wlhse.test;

import com.wlhse.dao.CheckListDao;
import com.wlhse.dao.QHSEManageSysElementsDao;
import com.wlhse.dto.inDto.QSHEMSElementInDto;
import com.wlhse.dto.outDto.QhseElementsOutDto;
import com.wlhse.entity.QhseElementsPojo;
import com.wlhse.exception.WLHSException;
import com.wlhse.util.SortCodeUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/*.xml"})
public class Test {


    @Resource
    private SortCodeUtil sortCodeUtil;
    @Resource
    private CheckListDao checkListDao;

    @Resource
    private QHSEManageSysElementsDao qHSEManageSysElementsDao;

    //导入excel采用！！！！！
//    @org.junit.Test
//    public void test1() throws Exception {
//        //从本地将excel存入db
//        uploadService.uploadCheckList("F:\\java后台安装工具\\TomCat\\webapps\\CheckList\\cc9120ee-5ca1-44bb-bc09-dcb2f0c0de32.xls");
//    }

    @org.junit.Test
    public void test2() throws Exception {
//        String code=sortCodeUtil.getMaxCodeString("00010001");
        List<String> list = new ArrayList<>();
        list.add("0001");
        list.add("0003");
        list.add("0004");
        list.add("0002");
        System.out.println(sortCodeUtil.getMaxCode(list));
    }

    @org.junit.Test
    public void test3() throws Exception {
        List<String> list = checkListDao.querryAllCheckListCode();
        for (String t : list) {
            System.out.println(t);
        }
    }

    @org.junit.Test
    public void insertProblemDescriptionTest() {
        // String[] description=problemDescription.split("([1-9][0-9]{0,1})");//用0-99的数字打断，中间为正则表达式
        //先把该code的问题描述全部删除，再添加。
        String description = "1.全部没有持有\n" +
                "2.行政领导未持有\n" +
                "3.党组织领导未持有\n" +
                "4.证件全部过期\n" +
                "5.行政领导证件过期\n" +
                "6.党组织领导证件过期\n" +
                "7.其他_______________";
        String b = description.replace("\n", "");
        System.out.println(b);
        if (description.startsWith("1")) {
            String[] s = description.split("1", 2);
            for (int i = 2; s[1].contains(String.valueOf(i)); i++) {
                description = s[1];
                s = description.split("\\n" + String.valueOf(i), 2);
                if (s[0].startsWith("."))
                    System.out.println(s[0].substring(1));
                else
                    System.out.println(s[0]);

            }
            if (s[1].startsWith("."))
                System.out.println(s[1].substring(1));
            else
                System.out.println(s[1]);
        } else {
            System.out.println("第一项的序号不是：1");
        }
    }


    //---------读取审核要素原表excel算法，自动生成记录，自动生成编码，自动分级，自动统计分数，节点数，自动分解问题描述，分别写入数据库-----
    //---对excel有格式要求，运行直接写入数据库，仅限算法作者使用。
    @org.junit.Test
    public void readComplexExcel() throws Exception {
        Workbook workbook;
        String filePath = "E:\\qshe\\zxQHSE管理体系要素一体化、标准化(1).xlsx";
        FileInputStream fis = null;
        fis = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);
        int endloc = sheet.getPhysicalNumberOfRows() - 1;//从0开始
        Map<String, Object> T1result = getTNNode(sheet, 2, endloc, "", 0);
        Map<Integer, int[]> T1RangeMap = (Map<Integer, int[]>) T1result.get("RangeMap");
        Map<Integer, String> T1CodeMap = (Map<Integer, String>) T1result.get("CodeMap");
        List<QSHEMSElementInDto> beanList1 = (List<QSHEMSElementInDto>) T1result.get("elementList");
        Map<String, String> problemDescriptionMap = new HashMap<>();

        for (Map.Entry<Integer, int[]> entry1 : T1RangeMap.entrySet())//第二层
        {
            //System.out.println(entry1.getKey()+"---"+entry1.getValue()[0]+"   "+entry1.getValue()[1]);
            Map<String, Object> T2result = getTNNode(sheet, entry1.getValue()[0], entry1.getValue()[1], T1CodeMap.get(entry1.getKey()), 1);
            Map<Integer, int[]> T2RangeMap = (Map<Integer, int[]>) T2result.get("RangeMap");
            Map<Integer, String> T2CodeMap = (Map<Integer, String>) T2result.get("CodeMap");
            List<QSHEMSElementInDto> beanList2 = (List<QSHEMSElementInDto>) T2result.get("elementList");
            beanList1.addAll(beanList2);

            for(Map.Entry<Integer,int[]> entry2:T2RangeMap.entrySet())//第三层
            {
                //System.out.println(entry2.getKey()+"---"+entry2.getValue()[0]+"   "+entry2.getValue()[1]);
                Map<String, Object> T3result=getTNNode(sheet,entry2.getValue()[0],entry2.getValue()[1],T2CodeMap.get(entry2.getKey()),2);
                Map<Integer, int[]> T3RangeMap=(Map<Integer, int[]>)T3result.get("RangeMap");
                Map<Integer, String> T3CodeMap=(Map<Integer, String>)T3result.get("CodeMap");
                List<QSHEMSElementInDto> beanList3=(List<QSHEMSElementInDto>)T3result.get("elementList");
                beanList1.addAll(beanList3);

                for(Map.Entry<Integer,int[]> entry3:T3RangeMap.entrySet())//第四层
                {
                    //System.out.println(entry3.getKey()+"---"+entry3.getValue()[0]+"   "+entry3.getValue()[1]);
                    Map<String, Object> T4result=getTNNode(sheet,entry3.getValue()[0],entry3.getValue()[1],T3CodeMap.get(entry3.getKey()),3);
                    Map<Integer, int[]> T4RangeMap=(Map<Integer, int[]>)T4result.get("RangeMap");
                    Map<Integer, String> T4CodeMap=(Map<Integer, String>)T4result.get("CodeMap");
                    List<QSHEMSElementInDto> beanList4=(List<QSHEMSElementInDto>)T4result.get("elementList");
                    beanList1.addAll(beanList4);

                    for(Map.Entry<Integer,int[]> entry4:T4RangeMap.entrySet())//第五层
                    {
                        //System.out.println(entry4.getKey()+"---"+entry4.getValue()[0]+"   "+entry4.getValue()[1]);
                        Map<String, Object> T5result=getTNNode(sheet,entry4.getValue()[0],entry4.getValue()[1],T4CodeMap.get(entry4.getKey()),4);
                        Map<Integer, int[]> T5RangeMap=(Map<Integer, int[]>)T5result.get("RangeMap");
                        Map<Integer, String> T5CodeMap=(Map<Integer, String>)T5result.get("CodeMap");
                        List<QSHEMSElementInDto> beanList5=(List<QSHEMSElementInDto>)T5result.get("elementList");
                        beanList1.addAll(beanList5);

                        for(Map.Entry<Integer,int[]> entry5:T5RangeMap.entrySet())//第六层
                        {
                            //System.out.println(entry5.getKey()+"---"+entry5.getValue()[0]+"   "+entry5.getValue()[1]);
                            Map<String, Object> T6result=getLastNode(sheet,entry5.getValue()[0],entry5.getValue()[1],T5CodeMap.get(entry5.getKey()),5);
                            List<QSHEMSElementInDto> beanList6=(List<QSHEMSElementInDto>)T6result.get("elementList");
                            Map<String, String> problemDescriptionMap1=(Map<String, String>)T6result.get("problemDescriptionMap");
                            beanList1.addAll(beanList6);
                            problemDescriptionMap.putAll(problemDescriptionMap1);
                        }
                    }
                }
            }
        }

        for(QSHEMSElementInDto ele:beanList1)
            System.out.println(ele);
        List<QhseElementsOutDto> TreeList=getQhseElementTree(beanList1);

        for(QhseElementsOutDto qshele:TreeList) {
            getScore(qshele);
            getCount(qshele);
        }
         for (QhseElementsOutDto ele1:TreeList)
            {
                System.out.println(ele1.getName() + "--" + ele1.getCode());
                qHSEManageSysElementsDao.addExcelQHSEElemenForInerPople(ele1);
                for (QhseElementsOutDto ele2 : ele1.getChildNode()) {
                    System.out.println(ele2.getName() + "--" + ele2.getCode());
                    qHSEManageSysElementsDao.addExcelQHSEElemenForInerPople(ele2);
                    for (QhseElementsOutDto ele3 : ele2.getChildNode()) {
                        System.out.println(ele3.getName() + "--" + ele3.getCode());
                        qHSEManageSysElementsDao.addExcelQHSEElemenForInerPople(ele3);
                        for (QhseElementsOutDto ele4 : ele3.getChildNode()) {
                            System.out.println(ele4.getName() + "--" + ele4.getCode());
                             qHSEManageSysElementsDao.addExcelQHSEElemenForInerPople(ele4);
                            for (QhseElementsOutDto ele5 : ele4.getChildNode()) {
                                System.out.println(ele5.getName() + "--" + ele5.getCode());
                                 qHSEManageSysElementsDao.addExcelQHSEElemenForInerPople(ele5);
                                for (QhseElementsOutDto ele6 : ele5.getChildNode()) {
                                    System.out.println(ele6.getName() + "--" + ele6.getCode());
                                    qHSEManageSysElementsDao.addExcelQHSEElemenForInerPople(ele6);
                                }//6
                            }//5
                        }//4
                    }//3
                }//2
            }
        insertProblemDescription(problemDescriptionMap);
        workbook.close();
        System.out.println("----关闭close");
        fis.close();
        System.out.println("----关闭fis" + "成功了，666");
    }

    public Map<String, Object> getTNNode(Sheet sheet, int start, int end, String parentCode, int rowNUmber) throws Exception {
        DataFormatter dataFormat = new DataFormatter();
        Row row;
        String value;
        int[] a = new int[2];
        Pattern pattern = Pattern.compile("[0-9]*");
        Map<Integer, String> T1CodeMap = new TreeMap<>();
        Map<Integer, int[]> T1RangeMap = new TreeMap<>();
        List<QSHEMSElementInDto> beanList = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();
        HashMap<String, String> QSHEMSElementValueMap = new HashMap<>();
        row = sheet.getRow(start);
        value=dataFormat.formatCellValue(row.getCell(rowNUmber));
        if (!(pattern.matcher(value.charAt(0) + "").matches()))
            start+=1;
        row = sheet.getRow(start);
        value=dataFormat.formatCellValue(row.getCell(rowNUmber));
        if (!(pattern.matcher(value.charAt(0) + "").matches()))
            start+=1;
        row = sheet.getRow(start);
        String f1Code = parentCode + "001";
        QSHEMSElementValueMap.put("code", f1Code);
        QSHEMSElementValueMap.put("name", dataFormat.formatCellValue(row.getCell(rowNUmber)));
        QSHEMSElementValueMap.put("status", "启用");
        QSHEMSElementInDto qSHEMSElement = new QSHEMSElementInDto();
        BeanUtils.populate(qSHEMSElement, QSHEMSElementValueMap);
        //对象放进进容器
        beanList.add(qSHEMSElement);
        int id = 1;
        a[0] = start;
        if(start>=end) {
            a[1]=end;
            T1RangeMap.put(id, a);
        }
        T1CodeMap.put(id, f1Code);
        for (int i = start + 1; i <= end; i++) {
            row = sheet.getRow(i);
            value = dataFormat.formatCellValue(row.getCell(rowNUmber));
            if (value == null || "".equals(value) || " ".equals(value)) {
                if (i == end) {
                    a[1] = i;
                    T1RangeMap.put(id, a);
                }
                continue;
            }
            if (pattern.matcher(value.charAt(0) + "").matches()) {
                //生成code
                f1Code = sortCodeUtil.getMaxCodeString(f1Code);
                //封装对象
                QSHEMSElementValueMap.put("code", f1Code);
                QSHEMSElementValueMap.put("name", value);
                QSHEMSElementValueMap.put("status", "启用");
                qSHEMSElement = new QSHEMSElementInDto();
                BeanUtils.populate(qSHEMSElement, QSHEMSElementValueMap);
                beanList.add(qSHEMSElement);
                //子节点范围定位
                a[1] = i - 1;
                T1RangeMap.put(id, a);
                a = new int[2];
                a[0] = i;
                //存储code
                id++;
                T1CodeMap.put(id, f1Code);
            }
            if (i == end) {
                a[1] = i;
                T1RangeMap.put(id, a);
            }
        }
        result.put("CodeMap",T1CodeMap);
        result.put("RangeMap",T1RangeMap);
        result.put("elementList",beanList);
        return result;
    }

    public Map<String, Object> getLastNode(Sheet sheet, int start, int end, String parentCode, int rowNUmber) throws Exception {
        DataFormatter dataFormat = new DataFormatter();
        Row row;
        String value;
        Map<String, String> problemDescriptionMap = new HashMap<>();
        Pattern pattern = Pattern.compile("[0-9]*");
        List<QSHEMSElementInDto> beanList = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();
        String f1Code = parentCode + "001";
        row = sheet.getRow(start);
        HashMap<String, String> QSHEMSElementValueMap = new HashMap<>();
        QSHEMSElementValueMap.put("code", f1Code);
        String valueTemp=dataFormat.formatCellValue(row.getCell(rowNUmber)).replace("\n","");
        QSHEMSElementValueMap.put("name", valueTemp);
        QSHEMSElementValueMap.put("auditMode", dataFormat.formatCellValue(row.getCell(rowNUmber+1)));
        QSHEMSElementValueMap.put("initialScore", dataFormat.formatCellValue(row.getCell(rowNUmber+2)));
        String temp=dataFormat.formatCellValue(row.getCell(rowNUmber+3)).replace("\n","");
        QSHEMSElementValueMap.put("formula", temp);
        QSHEMSElementValueMap.put("totalCount", "1");
        QSHEMSElementValueMap.put("status", "启用");
        QSHEMSElementInDto qSHEMSElement = new QSHEMSElementInDto();
        BeanUtils.populate(qSHEMSElement, QSHEMSElementValueMap);
        String problemTemp=dataFormat.formatCellValue(row.getCell(rowNUmber+4)).replace("\n","");
        if(problemTemp!=null||!("".equals(problemTemp)))
        {
            if(problemTemp.startsWith("1")) {//检查是不是以序号1开头
              problemDescriptionMap.put(f1Code, problemTemp);
            }
        }
        else
            throw new Exception(f1Code+"的可能问题序号不是1开始");
        //对象放进进容器
        beanList.add(qSHEMSElement);
        for (int i = start + 1; i <= end; i++) {
            row = sheet.getRow(i);
            value = dataFormat.formatCellValue(row.getCell(rowNUmber)).replace("\n","");
            if (value == null || "".equals(value) || " ".equals(value)) {
                continue;
            }
            if (pattern.matcher(value.charAt(0) + "").matches()) {
                //生成code
                f1Code = sortCodeUtil.getMaxCodeString(f1Code);
                //封装对象
                QSHEMSElementValueMap.put("code", f1Code);
                QSHEMSElementValueMap.put("name", value);
                QSHEMSElementValueMap.put("status", "启用");
                QSHEMSElementValueMap.put("auditMode", dataFormat.formatCellValue(row.getCell(rowNUmber+1)));
                QSHEMSElementValueMap.put("initialScore", dataFormat.formatCellValue(row.getCell(rowNUmber+2)));
                String temp2=dataFormat.formatCellValue(row.getCell(rowNUmber+3)).replace("\n","");
                QSHEMSElementValueMap.put("formula", temp2);
                qSHEMSElement = new QSHEMSElementInDto();
                BeanUtils.populate(qSHEMSElement, QSHEMSElementValueMap);
                beanList.add(qSHEMSElement);
                String problemTemp2=dataFormat.formatCellValue(row.getCell(rowNUmber+4)).replace("\n","");
                if(problemTemp2!=null||!("".equals(problemTemp2)))
                {
                    if(problemTemp2.startsWith("1")) {//检查是不是以序号1开头
                        problemDescriptionMap.put(f1Code, problemTemp2);
                    }
                }
                else
                    throw new Exception(f1Code+"的可能问题序号不是1开始");
            }
        }
        result.put("elementList",beanList);
        result.put("problemDescriptionMap",problemDescriptionMap);
        return result;
    }
    public List<QhseElementsOutDto> returnQhseElementList(Map<String, QhseElementsOutDto> map, List<Integer> code) {
        /*
        思想：创建一个list,然后遍历map里的节点,如果是一级节点，就放进list；
        如果不是一级节点，就去找他的父节点，找到后就把当前节点放进父节里的孩子list中，最后逐渐形成一棵树；
         */
        List<QhseElementsOutDto> result = new ArrayList<>();
        Collections.sort(code);
        for (Map.Entry<String, QhseElementsOutDto> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key.length() == code.get(0))//是一级节点，就放进list；
                result.add(entry.getValue());
            else {//如果不是一级节点，就去找他的父节点
                QhseElementsOutDto treeDto = map.get(key.substring(0, code.get(code.indexOf(key.length()) - 1)));
                if (null == treeDto)//父节孩子为空
                    continue;
                if (null == treeDto.getChildNode()) {//父节孩子为空，建一个list放入
                    List<QhseElementsOutDto> tmp = new ArrayList<>();
                    tmp.add(entry.getValue());
                    treeDto.setChildNode(tmp);
                } else////父节孩子不为空，直接放入
                    treeDto.getChildNode().add(entry.getValue());
            }
        }
        return result;
    }

    public List<QhseElementsOutDto> getQhseElementTree(List<QSHEMSElementInDto> qhseElementsPojos) {
        /*
        思想，把list里的对象逐个遍历；QhseElementsPojo赋值给QhseElementsOutDto，
        并把节点<code,QhseElementsOutDto>,放进map集合，方便后续操作；同时设一个code list记录节点长度；
         */
        Map<String, QhseElementsOutDto> map1 = new TreeMap<>();
        List<Integer> code = new ArrayList<>();
        for (QSHEMSElementInDto pojo : qhseElementsPojos) {
            QhseElementsOutDto qhseElementsOutDto = new QhseElementsOutDto();
            qhseElementsOutDto.setAuditMode(pojo.getAuditMode());
            qhseElementsOutDto.setCode(pojo.getCode());
            qhseElementsOutDto.setTotalCount(pojo.getTotalCount());
            qhseElementsOutDto.setFormula(pojo.getFormula());
            qhseElementsOutDto.setInitialScore(pojo.getInitialScore());
            qhseElementsOutDto.setName(pojo.getName());
            qhseElementsOutDto.setStatus(pojo.getStatus());
            map1.put(qhseElementsOutDto.getCode(), qhseElementsOutDto);
            //同一层节点长度一样
            if (code.indexOf(pojo.getCode().length()) == -1)
                code.add(pojo.getCode().length());
        }
        return returnQhseElementList(map1, code);
    }
   public Integer getScore(QhseElementsOutDto hseElementsOutDto)//递归求分数；
    {
        int score=0;
        if(hseElementsOutDto.getInitialScore()==null){
            if(hseElementsOutDto.getChildNode()==null)
                hseElementsOutDto.setInitialScore(0);
            else{
                for(QhseElementsOutDto ele:hseElementsOutDto.getChildNode()){
                    score+=getScore(ele);
                }
                hseElementsOutDto.setInitialScore(score);
            }
        }
        return hseElementsOutDto.getInitialScore();
    }
   public Integer getCount(QhseElementsOutDto hseElementsOutDto)//递归求分数；
    {
        int count=0;
        if(hseElementsOutDto.getTotalCount()==null){
            if(hseElementsOutDto.getChildNode()==null)
                hseElementsOutDto.setTotalCount(0);
            else{
                for(QhseElementsOutDto ele:hseElementsOutDto.getChildNode()){
                    count+=getCount(ele);
                }
                hseElementsOutDto.setTotalCount(count);
            }
        }
        return hseElementsOutDto.getTotalCount();
    }

    @Transactional
    public void insertProblemDescription(Map<String, String> problemDescription) {
        /*
        思想：算法升级，根据递增序号1，2，3，4....打断，能有效解决：1.占总数的1.5% 2注安占专职人员的比例等于20% 3注安占专职人员的比例在20%以下等格式
        但弊端是，序号必须是递增的，1，2，2，3就会打断失败。
         */
        String code;
        String description;
        //为了提高效率，直接把数据表先清空，然后再插入。使用事务管理防止插入失败造成数据丢失。
        qHSEManageSysElementsDao.deleteAllDescription();
        for (Map.Entry<String, String> entry : problemDescription.entrySet()) {
            code = entry.getKey();
            description = entry.getValue();
            System.out.println(code+"----"+description);
            String[] s = description.split("1", 2);
            for (int i = 2; s[1].contains(String.valueOf(i)); i++) {
                description = s[1];
                s = description.split(String.valueOf(i), 2);
                //得到的s[0]即为插入的问题描述；
                //有".",就去除，没有”."就直接写入
                if (qHSEManageSysElementsDao.addProblemDescription(code, (s[0].startsWith(".") ? s[0].substring(1) : s[0])) <= 0)
                    throw new WLHSException("新增失败");
            }//插入最后个问题描述，有".",就去除，没有”."就直接写入
            if (qHSEManageSysElementsDao.addProblemDescription(code, (s[1].startsWith(".") ? s[1].substring(1) : s[1])) <= 0)
                throw new WLHSException("新增失败");
        }
    }
}
