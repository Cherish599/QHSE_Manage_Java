package com.wlhse.dao;

import com.wlhse.entity.DashboardQualityManagement;
import com.wlhse.entity.DashboardRecorderManagement;
import com.wlhse.entity.DashboardScheduleManagement;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author tobing
 * @Date 2020/10/28 21:25
 * @Description Dashboard持久层接口
 */
@Repository
public interface DashboardDao {

    /**
     * 查询质量管理、质量报告计划进度
     *
     * @param qualityManagement 质量管理
     * @return R
     */
    List<DashboardQualityManagement> queryDashboardQualityManagement(DashboardQualityManagement qualityManagement);

    /**
     * 查询记录仪管理、记录仪使用情况
     *
     * @param recorderManagement 记录仪使用情况
     * @return R
     */
    List<DashboardRecorderManagement> queryDashboardRecorderManagement(DashboardRecorderManagement recorderManagement);

    /**
     * 查询标准进度管理
     *
     * @param scheduleManagement 标准进度管理
     * @return R
     */
    List<DashboardScheduleManagement> queryDashboardScheduleManagement(DashboardScheduleManagement scheduleManagement);

    /**
     * 更新【进度管理】数据
     *
     * @param dashboardScheduleManagement d
     * @return int
     */
    int updateDSM(DashboardScheduleManagement dashboardScheduleManagement);

    /**
     * 更新【质量管理】数据
     *
     * @param dashboardQualityManagement d
     * @return int
     */
    int updateDQM(DashboardQualityManagement dashboardQualityManagement);

    /**
     * 更新【记录仪管理】数据
     *
     * @param dashboardRecorderManagement d
     * @return int
     */
    int updateDRM(DashboardRecorderManagement dashboardRecorderManagement);

}
