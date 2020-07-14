package com.wlhse.service;

import com.wlhse.dto.inDto.ElementReviewDto;
import com.wlhse.dto.getDto.EmployeeDto;
import com.wlhse.dto.outDto.QHSECompanyYearManagerSysElementDto;
import com.wlhse.dto.outDto.QhseEvidenceAttatchDto;
import com.wlhse.util.R;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ElementReviewService {


    R query(ElementReviewDto elementReviewDto);

    R queryS(ElementReviewDto elementReviewDto);

    //修改审核人状态
    R updateStatus(ElementReviewDto elementReviewDto);

    R queryAll(QhseEvidenceAttatchDto qhseEvidenceAttatchDto);

}
