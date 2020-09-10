package com.wlhse.controller;

import com.alibaba.excel.EasyExcel;
import com.wlhse.dao.CompanyDao;
import com.wlhse.dao.MesSumDataDao;
import com.wlhse.dao.MonitorPlanDetailDao;
import com.wlhse.dto.MonitorPlan;
import com.wlhse.dto.MonitorPlanDetail;
import com.wlhse.entity.MesSumData;
import com.wlhse.entity.MonitorInputCheckRecord;
import com.wlhse.service.MonitorPlanService;
import com.wlhse.util.MesDataListener;
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

    @Resource
    CompanyDao companyDao;

    @Resource
    MesSumDataDao mesSumDataDao;

    //批量上传远程监控计划详情
    @RequestMapping(value = "/uploadMonitorPlanExcel",method = RequestMethod.POST)
    R addDetailsForMonitorPlan(@RequestParam(value = "file",required = false) MultipartFile file,@RequestParam(value = "planId",required = false)Integer planId) throws IOException {
        EasyExcel.read(file.getInputStream(), MonitorPlanDetail.class,
                new MonitorDataListener(monitorPlanDetailDao,planId)).
                sheet().doRead();
        return R.ok();
    }

    //创建新的远程监控计划
    @RequestMapping(value = "/createNewMonitorPlan",method = RequestMethod.POST)
    R createNewMonitorPlan(@RequestBody(required = false)MonitorPlan plan, HttpServletRequest request){
        return monitorPlanService.createNewMonitorPlan(plan,request);
    }

    //获取远程监控计划列表
    @RequestMapping(value = "/getMonitorPlanList",method = RequestMethod.GET)
    R getMonitorPlanList(HttpServletRequest request){
        return monitorPlanService.getMonitorPlanByPersonId(request);
    }


    //更改已上传的远程监控任务详情
    @RequestMapping(value = "/updateMonitorPlanDetail",method = RequestMethod.PUT)
    R updateMonitorPlanDetail(@RequestBody(required = false) MonitorPlanDetail monitorPlanDetail){
        return monitorPlanService.updateMonitorPlanDetail(monitorPlanDetail);
    }

    //删除远程监控计划
    @RequestMapping(value = "/deletePlan/{planId}",method = RequestMethod.DELETE)
    R deletePlan(@PathVariable("planId")Integer id){
        return monitorPlanService.deletePlan(id);
    }

    //删除远程监控计划详情
    @RequestMapping(value = "/deletePlanDetail/{detailId}",method = RequestMethod.DELETE)
    R deleteDetail(@PathVariable("detailId")Integer detailId){
        return monitorPlanService.deletePlanService(detailId);
    }

    //根据计划ID获取远程监控计划的全部详情内容
    @RequestMapping(value = "/getDetails/{planId}",method = RequestMethod.GET)
    R getDetails(@PathVariable("planId")Integer planId){
        return monitorPlanService.getDetails(planId);
    }

    /*2020-9-9新增的接口，还没写到接口文档里*/
    //单独新增远程监控计划详情项目
    @RequestMapping(value = "/createNewDetail",method = RequestMethod.POST)
    R createNewDetail(@RequestBody(required = false)MonitorPlanDetail monitorPlanDetail){
        return monitorPlanService.createNewDetailPlan(monitorPlanDetail);
    }

    //录入当天的详情内容
    @RequestMapping(value = "/inputDetail",method = RequestMethod.POST)
    R inputDetail(MonitorInputCheckRecord monitorInputCheckRecord,HttpServletRequest request){
        return monitorPlanService.insertNewInputRecord(monitorInputCheckRecord,request);
    }

    //获取当天录入的内容,有内容则代表当天已录入，无内容则代表当天未录入
    @RequestMapping(value = "/getInputtedDetailInfo/{detailId}",method = RequestMethod.GET)
    R getInputtedDetailInfo(@PathVariable("detailId")Integer detailId){
        return monitorPlanService.getRecordDetail(detailId);
    }

    //当天的详情信息已经录入，需要更新录入的信息（输入核查信息也要使用该接口）
    @RequestMapping(value = "/updateInputtedDetailInfo",method = RequestMethod.PUT)
    R updateInputtedDetailInfo(@RequestBody(required = false)MonitorInputCheckRecord monitorInputCheckRecord){
        return monitorPlanService.updateInputtedRecord(monitorInputCheckRecord);
    }
    //获取需要核查的远程监控记录详情
    @RequestMapping(value = "/getCheckDetail/{planId}",method = RequestMethod.GET)
    R getNeedToCheckRecords(@PathVariable("planId")Integer planId){
        return monitorPlanService.getNeedToCheckRecords(planId);
    }

    //上传
    @RequestMapping(value = "/uploadMesSumDataExcel",method = RequestMethod.POST)
    R uploadMesSumDataExcel(@RequestParam(value = "file",required = false) MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), MesSumData.class,
                new MesDataListener(mesSumDataDao,companyDao)).
                sheet().doRead();
        return R.ok();
    }

}
