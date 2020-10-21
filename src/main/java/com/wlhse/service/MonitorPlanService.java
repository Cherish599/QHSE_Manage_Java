package com.wlhse.service;

import com.wlhse.dto.MonitorPlan;
import com.wlhse.dto.MonitorPlanDetail;
import com.wlhse.entity.MonitorInputCheckRecord;
import com.wlhse.util.R;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

public interface MonitorPlanService {

    R createNewMonitorPlan(MonitorPlan monitorPlan, HttpServletRequest request);

    R getMonitorPlanByPersonId(HttpServletRequest request);

    R updateMonitorPlanDetail(MonitorPlanDetail monitorPlanDetail);

    R deletePlan(int id);

    R deletePlanService(int detailId);

    R getDetails(int planId);

    R createNewDetailPlan(MonitorPlanDetail monitorPlanDetail);

    R insertNewInputRecord(MonitorInputCheckRecord monitorInputCheckRecord,HttpServletRequest request);

    R updateInputtedRecord(MonitorInputCheckRecord monitorInputCheckRecord);

    R getRecordDetail(int detailId);

    R getNeedToCheckRecords(int planId,String date);

    R getInputDatesByPlanId(int planId) throws ParseException;

    //我发现这个似乎没什么用
    R deleteInputtedRecord(int detailId);

    R getRecordDetailByDate(int detailId,String date);

    R getNeedToCheckPlanDetails(int planId,String date);

    R getDayReport(int planId,String date);

    R endPlan(Integer planId);
}
