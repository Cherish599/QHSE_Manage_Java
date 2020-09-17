package com.wlhse.service;

import com.wlhse.dto.QualityCheckTableRecordAttachInfoDto;
import com.wlhse.dto.QualityCheckTableRecordDto;
import com.wlhse.util.R;

import java.util.List;

public interface QualityCheckTableRecordService {
    R queryCheckTreeByID(Integer qualityCheckID);

    R addInformAndAttach(QualityCheckTableRecordDto qualityCheckTableRecordDto, List<QualityCheckTableRecordAttachInfoDto> Plist);
}
