package com.wlhse.service.impl;

import com.wlhse.cache.JedisClient;
import com.wlhse.dao.EmployeeManagementDao;
import com.wlhse.dao.MonitorInputCheckDao;
import com.wlhse.dao.MonitorPlanDao;
import com.wlhse.dao.MonitorPlanDetailDao;
import com.wlhse.dto.EmployeeManagementDto;
import com.wlhse.dto.MonitorPlan;
import com.wlhse.dto.MonitorPlanDetail;
import com.wlhse.entity.MonitorInputCheckRecord;
import com.wlhse.service.MonitorPlanService;
import com.wlhse.util.R;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MonitorPlanServiceImp implements MonitorPlanService {
    @Resource
    MonitorPlanDao monitorPlanDao;
    @Resource
    JedisClient jedisClient;
    @Resource
    MonitorPlanDetailDao monitorPlanDetailDao;
    @Resource
    MonitorInputCheckDao monitorInputCheckDao;
    @Resource
    EmployeeManagementDao employeeManagementDao;
    @Override
    public R createNewMonitorPlan(MonitorPlan monitorPlan, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Map<String, String> map = jedisClient.hGetAll(token);
        int employeeId = Integer.valueOf(map.get("employeeId"));
        String employeeName = employeeManagementDao.queryEmployeeNameByEmployeeId(employeeId);
        monitorPlan.setPlanPersonName(employeeName);
        monitorPlan.setPlanPersonID(employeeId);
        monitorPlanDao.createNewMonitorPlan(monitorPlan);
        return R.ok();
    }

    @Override
    public R getMonitorPlanByPersonId(HttpServletRequest request) {
        R r=new R();
        String token = request.getHeader("Authorization");
        Map<String, String> map = jedisClient.hGetAll(token);
        int employeeId = Integer.valueOf(map.get("employeeId"));
        EmployeeManagementDto employeeManagementDto = employeeManagementDao.queryById(employeeId);
        List<MonitorPlan> monitorPlanByPlanPersonId = monitorPlanDao.getMonitorPlanByPlanCompanyCode(employeeManagementDto.getCompanyCode());
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

    @Override
    public R createNewDetailPlan(MonitorPlanDetail monitorPlanDetail) {
        monitorPlanDetailDao.createNewPlanDetail(monitorPlanDetail);
        return R.ok();
    }

    @Override
    public R insertNewInputRecord(MonitorInputCheckRecord monitorInputCheckRecord, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Map<String, String> map = jedisClient.hGetAll(token);
        int employeeId = Integer.valueOf(map.get("employeeId"));
        String employeeName = employeeManagementDao.queryEmployeeNameByEmployeeId(employeeId);
        monitorInputCheckRecord.setInputPersonID(employeeId);
        monitorInputCheckRecord.setInputPersonName(employeeName);
        //该计划录入次数++
        //获取录入次数
        String time = jedisClient.get("InputCnt" + monitorInputCheckRecord.getMonitorPlanID());
        jedisClient.set("InputCnt"+monitorInputCheckRecord.getMonitorPlanID(),time==null?String.valueOf(1):String.valueOf(Integer.valueOf(time
        )+1));
        monitorInputCheckDao.insertNewInputRecord(monitorInputCheckRecord);
        return R.ok();
    }

    @Override
    public R updateInputtedRecord(MonitorInputCheckRecord monitorInputCheckRecord) {
        monitorInputCheckDao.updateInputRecord(monitorInputCheckRecord);
        return R.ok();
    }

    @Override
    public R getRecordDetail(int detailId) {
        R r=new R();
        r.put("data",monitorInputCheckDao.getRecordDetail(detailId));
        return r;
    }

    @Override
    public R getNeedToCheckRecords(int planId,String date) {
        R r=new R();
        r.put("data",monitorInputCheckDao.getCheckMonitor(planId,date));
        return r;
    }

    @Override
    public R getInputDatesByPlanId(int planId) throws ParseException {
        MonitorPlan monitorPlan = monitorPlanDao.getBeginAndEndDate(planId);
        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
        Date beginD=fmt.parse(monitorPlan.getStartDate());
        Date endD=fmt.parse(monitorPlan.getEndDate());
        List lDate = new ArrayList();
        lDate.add(fmt.format(beginD));
        Calendar calBegin=Calendar.getInstance();
        calBegin.setTime(beginD);
        while (endD.after(calBegin.getTime())){
            calBegin.add(Calendar.DAY_OF_MONTH,1);
            lDate.add(fmt.format(calBegin.getTime()));
        }
        R r=new R();
        r.put("data",lDate);
        return r;
    }

    @Override
    public R deleteInputtedRecord(int detailId) {
        monitorInputCheckDao.deleteInputRecord(detailId);
        return R.ok();
    }

    @Override
    public R getRecordDetailByDate(int detailId, String date) {
        R r=new R();
        r.put("data",monitorInputCheckDao.getRecordDetailByDate(detailId,date));
        return r;
    }

    @Override
    public R getNeedToCheckPlanDetails(int planId,String date){
        R r=new R();
        r.put("data",monitorInputCheckDao.getNeedToCheckPlanDetails(planId,date));
        return r;
    }

    @Override
    public R getDayReport(int planId,String date) {
        R r=new R();
        r.put("data",monitorInputCheckDao.getDayReport(planId,date));
        return r;

    }

    @Override
    public R endPlan(Integer planId) {
        monitorPlanDao.endPlan(planId);
        return R.ok();
    }

    @Override
    public R getTotalInputTime(int planId) {
        R r=new R();
        r.put("data",jedisClient.get("InputCnt"+planId));
        return r;
    }

    @Override
    public R getInputAndCheckDetail(Integer planId) {
        R r=new R();
        r.put("data",monitorInputCheckDao.getInputAndCheckDetail(planId));
        return r;
    }
}
