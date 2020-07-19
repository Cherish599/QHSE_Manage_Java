package com.wlhse.service;

import com.wlhse.dto.RegulationRecordDto;
import com.wlhse.util.R;

public interface RegulationRecordService {

    //增
    R addRegulationRecord(RegulationRecordDto regulationRecordDto);
    //删
    R deleteRegulationRecord(Integer id);
    //改
    R updateRegulationRecord (RegulationRecordDto regulationRecordDto);
    //按ID查询
    String queryRegulationRecordById(Integer id);
    //查询
    String queryRegulationRecord(RegulationRecordDto regulationRecordDto);
}
