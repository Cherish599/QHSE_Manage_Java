package com.wlhse.dao;

import com.wlhse.entity.DashboardSecurityPojo;
import com.wlhse.entity.DashboardSecurityProjectPojo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author tobing
 * @Date 2020/11/21 21:06
 * @Description
 */
@Repository
public interface DashboardSecurityProjectDao {

    List<DashboardSecurityProjectPojo> queryDashboardSecurityProject(DashboardSecurityProjectPojo projectPojo);

    int countDashboardSecurityProject();

    void insertDashboardSecurityProject(DashboardSecurityProjectPojo projectPojo);

    void updateDashboardSecurityProject(DashboardSecurityProjectPojo projectPojo);
}
