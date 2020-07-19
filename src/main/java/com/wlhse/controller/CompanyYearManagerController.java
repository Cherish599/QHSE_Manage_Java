package com.wlhse.controller;


import com.wlhse.dto.inDto.CompanyYearManagerDto;
import com.wlhse.dto.inDto.ElementReviewDto;
import com.wlhse.dto.outDto.QhseEvidenceAttatchDto;
import com.wlhse.service.CompanyYearManagerService;
import com.wlhse.service.ElementReviewService;
import com.wlhse.util.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController("CompanyYearManagerController")
@RequestMapping("/api/v3")
public class CompanyYearManagerController {
    @Resource
    private CompanyYearManagerService companyYearManagerService;
    //显示管理表信息
    @RequestMapping(value = "/show_companyYearManager", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public R show(@ModelAttribute CompanyYearManagerDto companyYearManagerDto) {
        return companyYearManagerService.queryAll(companyYearManagerDto);
    }
    //状态修改
    @RequestMapping(value = "/approval_companyYearManager/{id}", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public R companyYearManager(@PathVariable int id) {
        return companyYearManagerService.updateStatus(id);
    }
    //删除记录
    @RequestMapping(value = "/delete_companyYearManager/{id}", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
    public R companyYearManager2(@PathVariable int id) {
        return companyYearManagerService.deleteALL(id);
    }
}
