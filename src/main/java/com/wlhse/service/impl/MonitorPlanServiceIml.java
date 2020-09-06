package com.wlhse.service.impl;

import com.wlhse.cache.JedisClient;
import com.wlhse.dao.MonitorPlanDao;
import com.wlhse.dao.MonitorPlanDetailDao;
import com.wlhse.dto.MonitorPlan;
import com.wlhse.dto.MonitorPlanDetail;
import com.wlhse.service.EmployeeManagementService;
import com.wlhse.service.MonitorPlanService;
import com.wlhse.util.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class MonitorPlanServiceIml implements MonitorPlanService {
    @Resource
    MonitorPlanDao monitorPlanDao;
    @Resource
    JedisClient jedisClient;
    @Resource
    EmployeeManagementService employeeManagementService;
    @Resource
    MonitorPlanDetailDao monitorPlanDetailDao;
    @Override
    public R createNewMonitorPlan(MonitorPlan monitorPlan, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Map<String, String> map = jedisClient.hGetAll(token);
        int employeeId = Integer.valueOf(map.get("employeeId"));
        String employeeName = employeeManagementService.getEmployeeNameByEmployeeID(employeeId);
        monitorPlan.setPlanPersonName(employeeName);
        monitorPlan.setPlanPersonID(employeeId);
        monitorPlanDao.createNewMonitorPlan(monitorPlan);
        return R.ok();
    }

    @Override
    public R getMonitorPlanByPersonId(HttpServletRequest request) {
        R r=new R();
        //TODO 目前是按照任务下达人的信息获取监控任务列表
        String token = request.getHeader("Authorization");
        Map<String, String> map = jedisClient.hGetAll(token);
        int employeeId = Integer.valueOf(map.get("employeeId"));
        List<MonitorPlan> monitorPlanByPlanPersonId = monitorPlanDao.getMonitorPlanByPlanPersonId(employeeId);
        r.put("data",monitorPlanByPlanPersonId);
        return r;
    }

    @Override
    public R updateMonitorPlanDetail(MonitorPlanDetail monitorPlanDetail) {
        monitorPlanDetailDao.updateDetail(monitorPlanDetail);
        return R.ok();
    }

    @Override
    public R deletePlan(int id) {
        monitorPlanDao.deletePlan(id);
        return R.ok();
    }

    @Override
    public R deletePlanService(int detailId) {
        monitorPlanDetailDao.deletePlanDetail(detailId);
        return R.ok();
    }

    @Override
    public R getDetails(int planId) {
        R r=new R();
        r.put("data",monitorPlanDetailDao.getDetailByPlanId(planId));
        return r;
    }
}
