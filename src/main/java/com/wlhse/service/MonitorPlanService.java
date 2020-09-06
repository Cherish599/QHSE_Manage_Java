package com.wlhse.service;

import com.wlhse.dto.MonitorPlan;
import com.wlhse.dto.MonitorPlanDetail;
import com.wlhse.util.R;

import javax.servlet.http.HttpServletRequest;

public interface MonitorPlanService {

    R createNewMonitorPlan(MonitorPlan monitorPlan, HttpServletRequest request);

    R getMonitorPlanByPersonId(HttpServletRequest request);

    R updateMonitorPlanDetail(MonitorPlanDetail monitorPlanDetail);

    R deletePlan(int id);

    R deletePlanService(int detailId);

    R getDetails(int planId);
}
