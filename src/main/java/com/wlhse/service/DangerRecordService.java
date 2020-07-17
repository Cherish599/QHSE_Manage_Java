package com.wlhse.service;

import com.wlhse.dto.DangerRecordDto;
import com.wlhse.util.R;

public interface DangerRecordService {
    // 增
    R addDangerRecord(DangerRecordDto dangerRecordDto);
    //删
    R deleteDangerRecord(Integer id);
    //改
    R updateDangerRecord (DangerRecordDto dangerRecordDto);
    //按ID查询
    String queryDangerRecordById(Integer id);
}
