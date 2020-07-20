package com.wlhse.service;

import com.wlhse.dto.inDto.CompanyYearManagerDto;
import com.wlhse.dto.inDto.ElementReviewDto;
import com.wlhse.dto.outDto.QhseEvidenceAttatchDto;
import com.wlhse.util.R;

public interface CompanyYearManagerService {

    //修改审核人状态
    R updateStatus(int id);
    //查询信息
    R queryAll(CompanyYearManagerDto companyYearManagerDto);
    //删除信息
    R deleteALL(int id);
    //新增年度检查表
    R addCompanyYearManager(CompanyYearManagerDto companyYearManagerDto);
}
