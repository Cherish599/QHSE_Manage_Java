package com.wlhse.dao;

import com.wlhse.dto.MonitorPlanDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitorPlanDetailDao {

    int batchInsertNewRecord(List<MonitorPlanDetail> monitorPlanDetails);

    int updateDetail(MonitorPlanDetail monitorPlanDetail);

    int deletePlanDetail(int id);

    List<MonitorPlanDetail> getDetailByPlanId(int planId);

    int  createNewPlanDetail(MonitorPlanDetail monitorPlanDetail);

}
