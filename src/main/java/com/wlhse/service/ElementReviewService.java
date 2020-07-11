package com.wlhse.service;

import com.wlhse.dto.inDto.ElementReviewDto;
import com.wlhse.dto.getDto.EmployeeDto;
import com.wlhse.dto.outDto.QHSECompanyYearManagerSysElementDto;
import com.wlhse.util.R;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ElementReviewService {

    //查询审核表
    R query(ElementReviewDto elementReviewDto);

    //修改审核人状态
    R updateStatus(ElementReviewDto elementReviewDto);

    R queryAll( Integer id);

}
