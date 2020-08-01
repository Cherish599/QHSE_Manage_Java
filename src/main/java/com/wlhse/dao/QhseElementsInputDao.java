package com.wlhse.dao;

import com.wlhse.dto.inDto.ElementEvidenceAttachInDto;
import com.wlhse.dto.inDto.ElementEvidenceInDto;
import com.wlhse.dto.inDto.ElementReviewDto;
import com.wlhse.dto.outDto.QHSECompanyYearManagerSysElementEvidenceDto;
import com.wlhse.entity.QHSECompanySysElementsPojo;
import com.wlhse.entity.QHSEManageSysElements;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QhseElementsInputDao {
    //查询证据附件
    ElementEvidenceAttachInDto query(ElementEvidenceAttachInDto elementEvidenceAttachInDto);

    //增加证据附件
    int add(ElementEvidenceAttachInDto elementEvidenceAttachInDto);

    //增加附件
    int addAttach(ElementEvidenceAttachInDto elementEvidenceAttachInDto);

    //修改证据
    int update(ElementEvidenceAttachInDto elementEvidenceAttachInDto);

    //修改附件
    int updateAttach(ElementEvidenceAttachInDto elementEvidenceAttachInDto);

    //更改状态
    int updateStatus(Integer id);

    List<Integer> getCompanyManagerSysElementId(int tableId);

    int deleteFromCompanyManagerSysElement(int tableId);

    List<Integer> getCompanyYearManagerSysElementEvidenceId(int elementId);

    int deleteFromCompanyYearManagerSysElementEvidence(int elementId);

    int deleteFromCompanyYearManagerSysElementEvidenceAttach(int evidenceId);
}
