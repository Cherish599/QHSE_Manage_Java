package com.wlhse.dao;

import com.wlhse.dto.inDto.ElementEvidenceAttachInDto;
import com.wlhse.dto.inDto.ElementEvidenceInDto;
import com.wlhse.dto.inDto.ElementReviewDto;
import com.wlhse.dto.outDto.QHSECompanyYearManagerSysElementEvidenceDto;
import com.wlhse.entity.QHSECompanySysElementsPojo;
import com.wlhse.entity.QHSEManageSysElements;
import com.wlhse.entity.QhseElementsPojo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QhseElementsInputDao {

    QHSECompanyYearManagerSysElementEvidenceDto queryElementsEvidence(ElementEvidenceInDto elementEvidenceInDto);

    int addElementsEvidence(ElementEvidenceInDto elementEvidenceInDto);

    int updateElementsStatus(Integer id);

    int addElementsEvidenceAttach(ElementEvidenceAttachInDto elementEvidenceAttachInDto);

    int updateElementsEvidenceAttach(ElementEvidenceAttachInDto elementEvidenceAttachInDto);

    ElementEvidenceAttachInDto queryEvidenceAttach(Integer id);

    Integer selectElementTableID(Integer id);

    Integer updateElementTableByID(ElementEvidenceInDto elementEvidenceInDto);

}
