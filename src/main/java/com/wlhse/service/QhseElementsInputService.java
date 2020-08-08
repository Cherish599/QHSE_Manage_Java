package com.wlhse.service;

import com.wlhse.dto.inDto.ElementEvidenceAttachInDto;
import com.wlhse.entity.ElementInputFileInfo;
import com.wlhse.util.R;


public interface QhseElementsInputService {

    R addElementEvidenceAttach(ElementEvidenceAttachInDto elementEvidenceAttachInDto);
    R queryAll(ElementEvidenceAttachInDto elementEvidenceAttachInDto);
    String queryOriginFileName (String newElementFileName);
    void insertNewOriginFileName(ElementInputFileInfo elementInputFileInfo);
  //  void updateNewOriginFileName(ElementInputFileInfo elementInputFileInfo);

}
