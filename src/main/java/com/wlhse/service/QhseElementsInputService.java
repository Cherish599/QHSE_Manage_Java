package com.wlhse.service;

import com.wlhse.dto.inDto.ElementEvidenceAttachInDto;
import com.wlhse.dto.inDto.ElementEvidenceInDto;
import com.wlhse.dto.inDto.ElementReviewDto;
import com.wlhse.entity.QHSEManageSysElements;
import com.wlhse.util.R;

import java.util.List;


public interface QhseElementsInputService {

    R querryElementEvidence(ElementEvidenceInDto elementEvidenceInDto);

    R addElementEvidence(ElementEvidenceInDto elementEvidenceInDto);

    R addElementEvidenceAttach(ElementEvidenceAttachInDto elementEvidenceAttachInDto);

}
