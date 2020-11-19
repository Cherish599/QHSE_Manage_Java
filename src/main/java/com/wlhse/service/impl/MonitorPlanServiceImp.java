package com.wlhse.service.impl;

import com.alibaba.excel.EasyExcel;
import com.wlhse.cache.JedisClient;
import com.wlhse.dao.*;
import com.wlhse.dto.EmployeeManagementDto;
import com.wlhse.dto.MonitorPlan;
import com.wlhse.dto.MonitorPlanDetail;
import com.wlhse.dto.inDto.MesSearchCondition;
import com.wlhse.dto.outDto.MesDataOutDto;
import com.wlhse.entity.MesSumData;
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

    @Resource
    MesSumDataDao mesSumDataDao;
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
        if (monitorInputCheckRecord.getCondition().equals("备用")){
            monitorInputCheckRecord.setCheckStatus("未核查");
        }
        else {
            monitorInputCheckRecord.setCheckStatus("无需核查");
        }
        monitorInputCheckRecord.setInputPersonID(employeeId);
        monitorInputCheckRecord.setInputPersonName(employeeName);
        //该计划录入次数++
        //获取录入次数
        int monitorPlanID = monitorInputCheckRecord.getMonitorPlanID();
        String time = jedisClient.get("InputCnt" + monitorPlanID);
        jedisClient.set("InputCnt"+ monitorPlanID,time==null?String.valueOf(1):String.valueOf(Integer.valueOf(time
        )+1));

        monitorInputCheckDao.insertNewInputRecord(monitorInputCheckRecord);
        return R.ok();
    }

    @Override
    public R updateInputtedRecord(MonitorInputCheckRecord monitorInputCheckRecord) {
        //判断核查状态if
        if(monitorInputCheckRecord.getResult().equals("在用"))
        {
            //更新此项记录的状态为在用
           monitorInputCheckRecord.setCondition("在用");
        }
        monitorInputCheckRecord.setCheckStatus("已核查");
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
    public R getTotalInputTime(int planId) {
        R r=new R();
        r.put("data",jedisClient.get("InputCnt"+planId));
        return r;
    }

    @Override
    public R getInputAndCheckDetail(Integer detailId) {
        R r=new R();
        r.put("data",monitorInputCheckDao.getInputAndCheckDetail(detailId));
        return r;
    }

    @Override
    public R deleteInputInfo(int checkRecordId) {
        monitorInputCheckDao.deleteInputInfo(checkRecordId);
        return R.ok();
    }

    @Override
    public R getItemNum(int planId) {
        R r=new R();
        r.put("data",monitorInputCheckDao.getItemNum(planId));
        return r;
    }

    @Override
    public R endDetail(int detailId) {
        monitorPlanDetailDao.endDetail(detailId);
        return R.ok();
    }

    @Override
    public R refreshMesData(String date, String companyName) {
        //先从录入和核查记录获取需要动态加载的数据
        mesSumDataDao.getPlanDeviceNum(companyName);
        return null;
    }

    @Override
    public void downloadMonitorData(String date,int planId) {
        //根据统计书记
        String[] split = date.split(",");
        //先根据日期判断有哪些详情
        List<Integer> detailId;
        String fileName;
        List<MesSumData> data;
        if (split.length==1){
            fileName=split[0]+"日统计数据.xlsx";
            detailId=monitorPlanDetailDao.getDetailIdByDate(split[0]);
            //根据detailId去收集数据
            data=monitorPlanDetailDao.getMesDataByDate(split[0],detailId);
        }
        else {
            fileName=split[0]+"至"+split[1]+"日统计数据.xlsx";
            data=monitorPlanDetailDao.getMesDataByDates(split[0],split[1]);
        }
        EasyExcel.write(fileName, MesSumData.class).sheet("统计数据").doWrite(data);
    }

    @Override
    public R getDeviceTrend(String companyName) {
        R r=new R();
        List<Object> resultList=new ArrayList<>();
        List<String> time;
        List<MesDataOutDto> series=new ArrayList<>();
        time=monitorPlanDao.getDeviceUseDate(companyName);
        resultList.add(time);
        MesSearchCondition mesSearchCondition=new MesSearchCondition();
        mesSearchCondition.setCompanyName(companyName);
        mesSearchCondition.setType("覆盖率");
        MesDataOutDto mesDataOutDto=new MesDataOutDto();
        mesDataOutDto.setName("覆盖率");
        List<Double> rate=mesSumDataDao.getRate(mesSearchCondition);
        mesDataOutDto.setData(rate);
        series.add(mesDataOutDto);
        MesDataOutDto mesDataOutDto1=new MesDataOutDto();
        mesDataOutDto1.setName("使用率");
        mesSearchCondition.setType("使用率");
        List<Double> rate1=mesSumDataDao.getRate(mesSearchCondition);
        mesDataOutDto1.setData(rate1);
        series.add(mesDataOutDto1);
        MesDataOutDto mesDataOutDto2=new MesDataOutDto();
        mesDataOutDto2.setName("利用率");
        mesSearchCondition.setType("利用率");
        mesDataOutDto2.setData(mesSumDataDao.getRate(mesSearchCondition));
        series.add(mesDataOutDto2);
        resultList.add(series);
        r.put("data",resultList);
        return r;
    }
}
