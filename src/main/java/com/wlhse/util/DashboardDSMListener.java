package com.wlhse.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.wlhse.dao.DashboardDao;
import com.wlhse.entity.DashboardScheduleManagement;
import com.wlhse.entity.DashboardScheduleManagement;

import java.util.ArrayList;

public class DashboardDSMListener extends AnalysisEventListener<DashboardScheduleManagement> {

    private DashboardDao dashboardDao;

    private ArrayList<DashboardScheduleManagement> list
            = new ArrayList<>();

    public DashboardDSMListener(DashboardDao dashboardDao) {
        this.dashboardDao = dashboardDao;
    }

    @Override
    public void invoke(DashboardScheduleManagement qualityManagement, AnalysisContext analysisContext) {
        list.add(qualityManagement);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

        for (DashboardScheduleManagement dashboardScheduleManagement : list) {
            // 数据完整性判断
            if (dashboardScheduleManagement.getPlanNum() != null &&
                    dashboardScheduleManagement.getFirstDraftFinishNum() != null &&
                    dashboardScheduleManagement.getReviewPassNum() != null &&
                    dashboardScheduleManagement.getStandardReleaseNum() != null) {
                dashboardDao.updateDSM(dashboardScheduleManagement);
            }
        }
    }
}
