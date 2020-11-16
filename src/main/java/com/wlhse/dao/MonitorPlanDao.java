package com.wlhse.dao;

import com.wlhse.dto.MonitorPlan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitorPlanDao {

    int createNewMonitorPlan(MonitorPlan monitorPlan);

    List<MonitorPlan> getMonitorPlanByPlanCompanyCode(String companyCode);

    int deletePlan(int planId);

    MonitorPlan getBeginAndEndDate(int planId);

    int endPlan(Integer planId);

    int setCheckStatus(int monitorPlanID,String checkStatus);
}
