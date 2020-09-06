package com.wlhse.controller;

import com.alibaba.excel.EasyExcel;
import com.wlhse.dao.MonitorPlanDetailDao;
import com.wlhse.dto.MonitorPlan;
import com.wlhse.dto.MonitorPlanDetail;
import com.wlhse.service.MonitorPlanService;
import com.wlhse.util.MonitorDataListener;
import com.wlhse.util.R;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/api/v3")
public class MonitorController {

    @Resource
    MonitorPlanDetailDao monitorPlanDetailDao;

    @Resource
    MonitorPlanService monitorPlanService;

    @RequestMapping(value = "/uploadMonitorPlanExcel",method = RequestMethod.POST)
    R addDetailsForMonitorPlan(@RequestParam(value = "file",required = false) MultipartFile file,@RequestParam(value = "planId",required = false)Integer planId) throws IOException {
        EasyExcel.read(file.getInputStream(), MonitorPlanDetail.class,
                new MonitorDataListener(monitorPlanDetailDao,planId)).
                sheet().doRead();
        return R.ok();
    }

    @RequestMapping(value = "/createNewMonitorPlan",method = RequestMethod.POST)
    R createNewMonitorPlan(@RequestBody(required = false)MonitorPlan plan, HttpServletRequest request){
        return monitorPlanService.createNewMonitorPlan(plan,request);
    }

    @RequestMapping(value = "/getMonitorPlanList",method = RequestMethod.GET)
    R getMonitorPlanList(HttpServletRequest request){
        return monitorPlanService.getMonitorPlanByPersonId(request);
    }

    @RequestMapping(value = "/updateMonitorPlanDetail",method = RequestMethod.PUT)
    R updateMonitorPlanDetail(@RequestBody(required = false) MonitorPlanDetail monitorPlanDetail){
        return monitorPlanService.updateMonitorPlanDetail(monitorPlanDetail);
    }

    @RequestMapping(value = "/deletePlan/{planId}",method = RequestMethod.DELETE)
    R deletePlan(@PathVariable("planId")Integer id){
        return monitorPlanService.deletePlan(id);
    }

    @RequestMapping(value = "/deletePlanDetail/{detailId}",method = RequestMethod.DELETE)
    R deleteDetail(@PathVariable("detailId")Integer detailId){
        return monitorPlanService.deletePlanService(detailId);
    }

    @RequestMapping(value = "/getDetails/{planId}",method = RequestMethod.GET)
    R getDetails(@PathVariable("planId")Integer planId){
        return monitorPlanService.getDetails(planId);
    }
}
