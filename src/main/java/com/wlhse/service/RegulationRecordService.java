package com.wlhse.service;

import com.wlhse.dto.RegulationRecordDto;
import com.wlhse.util.R;

public interface RegulationRecordService {

    //增
    R addRegulationRecord(RegulationRecordDto regulationRecordDto);
    //删
    R deleteRegulationRecord(RegulationRecordDto regulationRecordDto);
    //改
    R updateRegulationRecord (RegulationRecordDto regulationRecordDto);
    //按ID查询
    R queryRegulationRecordById(Integer id);
    //查询
    R queryRegulationRecord(RegulationRecordDto regulationRecordDto);
}
