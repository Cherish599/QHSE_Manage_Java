package com.wlhse.service.impl;

import com.wlhse.dao.CheckListDao;
import com.wlhse.dto.*;
import com.wlhse.service.UploadService;
import com.wlhse.util.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
}
