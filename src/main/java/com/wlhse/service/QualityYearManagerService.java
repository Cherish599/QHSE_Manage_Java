package com.wlhse.service;


import com.wlhse.dto.CompanyYearManagerDtoWithEmployeeId;
import com.wlhse.dto.QualityYearManagerDtoWithEmployeeId;
import com.wlhse.util.R;

import javax.servlet.http.HttpServletRequest;

public interface QualityYearManagerService {

     R queryAll(QualityYearManagerDtoWithEmployeeId companyYearManagerDto, HttpServletRequest request);

     R deleteALL(int id);

     public R addCompanyYearManager(QualityYearManagerDtoWithEmployeeId companyYearManagerDto, HttpServletRequest request);
}
