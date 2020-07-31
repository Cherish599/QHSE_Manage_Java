package com.wlhse.service;

import com.wlhse.dto.inDto.ElementEvidenceAttachInDto;
import com.wlhse.dto.inDto.ElementEvidenceInDto;
import com.wlhse.dto.inDto.ElementReviewDto;
import com.wlhse.entity.QHSEManageSysElements;
import com.wlhse.util.R;


public interface QhseElementsInputService {

    R addElementEvidenceAttach(ElementEvidenceAttachInDto elementEvidenceAttachInDto);
    R queryAll(ElementEvidenceAttachInDto elementEvidenceAttachInDto);


}
