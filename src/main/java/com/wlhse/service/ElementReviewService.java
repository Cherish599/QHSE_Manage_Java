package com.wlhse.service;

import com.wlhse.dto.inDto.ElementReviewDto;
import com.wlhse.dto.outDto.QHSECompanyYearManagerSysElementDto;
import com.wlhse.dto.outDto.QhseEvidenceAttatchDto;
import com.wlhse.util.R;

import java.text.ParseException;
import java.util.List;

public interface ElementReviewService {


    R query(ElementReviewDto elementReviewDto);

    R queryS(ElementReviewDto elementReviewDto);

    //修改审核人状态
    R updateStatus(ElementReviewDto elementReviewDto);

    R queryAll(QhseEvidenceAttatchDto qhseEvidenceAttatchDto) throws ParseException;

    List<QHSECompanyYearManagerSysElementDto>  queryParent(String code);

    //添加审核人
    int updateCheck(ElementReviewDto elementReviewDto);

    int updateApprove(ElementReviewDto elementReviewDto);

    //不通过删除附件信息
    R deletes(ElementReviewDto elementReviewDto);

    //查询已审核
    R shows(ElementReviewDto elementReviewDto);

    //查询全要素
    R queryAllElement(ElementReviewDto elementReviewDto);
}
