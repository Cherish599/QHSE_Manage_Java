package com.wlhse.controller;

import com.wlhse.service.DashboardSecurityService;
import com.wlhse.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author tobing
 * @Date 2020/11/21 20:44
 * @Description 大屏-安全管理
 */
@RestController
@RequestMapping("/api/v3")
public class DashboardSecurityController {

    @Autowired
    private DashboardSecurityService dashboardSecurityService;


    /**
     * 查询【安全管理】大屏
     *
     * @return R
     */
    @RequestMapping(value = "/queryDashboardSecurity", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public R queryDashboardSecurity(@RequestParam(value = "companyCode" ,defaultValue = "") String companyCode) {
        return dashboardSecurityService.queryDashboardSecurity(companyCode);
    }

    /**
     * 下载【安全管理】模板
     *
     * @return R
     * @throws IOException io
     */
    @RequestMapping(value = "/downloadDashboardSecurityTemplate", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadDashboardSecurityTemplate() throws IOException {
        ResponseEntity<byte[]> res = null;
        try {
            res = dashboardSecurityService.downloadDashboardSecurityTemplate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 上传【安全管理】数据更新
     *
     * @param file file
     * @return R
     */
    @RequestMapping(value = "/uploadDashboardSecurity", method = RequestMethod.POST)
    public R uploadDashboardSecurity(@RequestParam(value = "file", required = false) MultipartFile file) {
        return dashboardSecurityService.uploadDashboardSecurity(file);
    }


}
