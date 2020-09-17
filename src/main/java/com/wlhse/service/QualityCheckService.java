package com.wlhse.service;

import com.wlhse.dto.QualityCheckDto;
import com.wlhse.util.R;

public interface QualityCheckService {
    R addQualityCheck(QualityCheckDto qualityCheckDto);
    R deleteQualityCheck(Integer qualityCheckID);
    R updateQualityCheck(QualityCheckDto qualityCheckDto);
    R queryAllTable();
    R queryTableByYearAndCom(QualityCheckDto qualityCheckDto);
    R pushTable(Integer qualityCheckID);
    R queryTableByYearAndComAndPush(QualityCheckDto qualityCheckDto);
}
