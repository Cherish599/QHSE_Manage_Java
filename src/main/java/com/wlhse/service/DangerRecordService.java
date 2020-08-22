package com.wlhse.service;

import com.wlhse.dto.DangerRecordDto;
import com.wlhse.util.R;

import javax.servlet.http.HttpServletRequest;

public interface DangerRecordService {
    // 增
    R addDangerRecord(DangerRecordDto dangerRecordDto, HttpServletRequest request);
    //删
    R deleteDangerRecord(DangerRecordDto dangerRecordDto);
    //改
    R updateDangerRecord(DangerRecordDto dangerRecordDto);
    //按ID查询
    String queryDangerRecordById(Integer id);
    //查询
    String queryDangerRecord(DangerRecordDto dangerRecordDto);
}
