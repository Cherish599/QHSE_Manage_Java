package com.wlhse.service;


import com.wlhse.dto.CheckRecordDto;
import com.wlhse.util.R;

public interface CheckRecordService {

    R addCheckRecord(CheckRecordDto checkRecordDto);

    R queryAll();

    R queryAllTree();

    R updateCheckrecord(int id, CheckRecordDto checkRecordDto);

    R deleteCheckrecord(int id);
}
