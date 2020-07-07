package com.wlhse.dao;

import com.wlhse.dto.CheckListDto;
import com.wlhse.dto.CheckRecordDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CheckRecordDao {

    Integer addCheckRecord(CheckRecordDto checkRecordDto);

}
