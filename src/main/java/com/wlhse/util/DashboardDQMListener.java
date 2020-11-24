package com.wlhse.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.wlhse.dao.DashboardDao;
import com.wlhse.dao.MonitorPlanDetailDao;
import com.wlhse.dto.MonitorPlanDetail;
import com.wlhse.entity.DashboardQualityManagement;

import java.util.ArrayList;
import java.util.List;

public class DashboardDQMListener extends AnalysisEventListener<DashboardQualityManagement> {

    private DashboardDao dashboardDao;

    private ArrayList<DashboardQualityManagement> list
            = new ArrayList<>();

    public DashboardDQMListener(DashboardDao dashboardDao) {
        this.dashboardDao = dashboardDao;
    }

    @Override
    public void invoke(DashboardQualityManagement qualityManagement, AnalysisContext analysisContext) {
        list.add(qualityManagement);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        for (DashboardQualityManagement dashboardQualityManagement : list) {
            // 数据完整性判断
            if (dashboardQualityManagement.getMonthFinishNum() != null &&
                    dashboardQualityManagement.getMonthPlanNum() != null) {
                dashboardDao.updateDQM(dashboardQualityManagement);
            }
        }
    }
}
