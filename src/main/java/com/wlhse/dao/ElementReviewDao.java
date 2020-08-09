package com.wlhse.dao;

import com.wlhse.dto.inDto.ElementReviewDto;
import com.wlhse.dto.outDto.QHSECompanyYearManagerSysElementDto;
import com.wlhse.dto.outDto.QhseEvidenceAttatchDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ElementReviewDao")
public interface ElementReviewDao {

    //查询审核表(审核人）
    List<QHSECompanyYearManagerSysElementDto> query(ElementReviewDto elementReviewDto);

    //查询审核表(批准人）
    List<QHSECompanyYearManagerSysElementDto> queryS(ElementReviewDto elementReviewDto);

    //修改审核人状态
    int update(ElementReviewDto elementReviewDto);

    //查询证据
    List<QhseEvidenceAttatchDto> queryAll(QhseEvidenceAttatchDto qhseEvidenceAttatchDto);

    //查父节点
    List<QHSECompanyYearManagerSysElementDto> queryParent(String code);

    //添加审核人
    int updateCheck(ElementReviewDto elementReviewDto);

    int updateApprove(ElementReviewDto elementReviewDto);

    //不通过删除证据
    int delete(ElementReviewDto elementReviewDto);

    int deleteAttach(ElementReviewDto elementReviewDto);

    int deleteNewOriginFile(ElementReviewDto elementReviewDto);


}
