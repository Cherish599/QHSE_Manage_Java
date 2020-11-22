package com.wlhse.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.wlhse.dao.CompanyDao;
import com.wlhse.dao.DashboardSecurityDao;
import com.wlhse.entity.CompanyPojo;
import com.wlhse.entity.DashboardSecurityPojo;
import com.wlhse.service.DashboardSecurityService;
import com.wlhse.util.DashboardSecurityListener;
import com.wlhse.util.R;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author tobing
 * @Date 2020/11/21 21:07
 * @Description
 */
@Service
@Transactional
public class DashboardSecurityServiceImpl implements DashboardSecurityService {

    @Autowired
    private DashboardSecurityDao dashboardSecurityDao;
    @Autowired
    private CompanyDao companyDao;

    @Override
    public R uploadDashboardSecurity(MultipartFile file) {
        if (file == null) {
            return R.error("文件不能为空");
        }
        try {
            EasyExcel.read(file.getInputStream(), DashboardSecurityPojo.class,
                    new DashboardSecurityListener(dashboardSecurityDao)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("文件异常");
        }
        return R.ok("更新成功");
    }

    @Override
    public ResponseEntity<byte[]> downloadDashboardSecurityTemplate() throws IOException {
        String path = System.getProperty("catalina.home") + "\\webapps\\" + "DashboardTemplate";
//        String path = "D:\\fileTest";
        File file = new File(path + File.separator + "查患纠违事件季度完成情况.xlsx");
        // 文件不存在：创建模板文件
        if (!file.exists()) {
            createDashboardSecurityTemplate(file);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        String fileName = "查患纠违事件季度完成情况.xlsx";
        String downloadFileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
        headers.setContentDispositionFormData("attachment", downloadFileName);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }

    @Override
    public R queryDashboardSecurity(String companyCode) {
        DashboardSecurityPojo dashboardSecurityPojo = new DashboardSecurityPojo();
        dashboardSecurityPojo.setCompanyCode(companyCode);
        List<DashboardSecurityPojo> dashboardSecurityPojoList = null;
        try {
            dashboardSecurityPojoList = dashboardSecurityDao.queryDashboardSecurity(dashboardSecurityPojo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (dashboardSecurityPojoList == null || dashboardSecurityPojoList.size() == 0) {
            return R.ok();
        }
        return R.ok().put("data", dashboardSecurityPojoList.get(0));
    }

    /**
     * 创建模板文件
     *
     * @param file file
     * @throws IOException io
     */
    private void createDashboardSecurityTemplate(File file) throws IOException {
        List<DashboardSecurityPojo> dashboardSecurityPojoList = new ArrayList<>();
        // 查询所有公司数据
        List<CompanyPojo> companyPojoList = companyDao.queryQhseCompany();
        for (int i = 0; i < companyPojoList.size(); i++) {
            CompanyPojo companyPojo = companyPojoList.get(i);
            if (companyPojo.getCompanyCode() == null || companyPojo.getCompanyCode().length() != 6) {
                companyPojoList.remove(i);
                continue;
            }
            DashboardSecurityPojo dashboardSecurityPojo = new DashboardSecurityPojo();
            dashboardSecurityPojo.setCompanyName(companyPojo.getName());
            dashboardSecurityPojo.setCompanyCode(companyPojo.getCompanyCode());
            dashboardSecurityPojoList.add(dashboardSecurityPojo);
        }
        EasyExcel.write(file, DashboardSecurityPojo.class).
                sheet("模板").doWrite(dashboardSecurityPojoList);
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcel.write(file, DashboardSecurityPojo.class).build();
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            excelWriter.write(dashboardSecurityPojoList, writeSheet);
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }
}
