package com.wlhse.dao;

import com.wlhse.dto.DangerRecordDto;
import com.wlhse.dto.DangerRecordOutDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DangerRecordDao {

    //新增隐患记录
    int addDangerRecord(DangerRecordDto dangerRecordDto);
    //删除隐患记录
    int deleteDangerRecord(DangerRecordDto dangerRecordDto);
    //修改隐患记录
    int updateDangerRecord(DangerRecordDto dangerRecordDto);

    //按ID查询隐患记录
    default List<DangerRecordOutDto> queryDangerRecordById(@Param("id") Integer id) {
        return null;
    }

    //条件查询隐患记录
    int queryTotal(DangerRecordDto dangerRecordDto);
    List<DangerRecordOutDto> queryDangerRecord(DangerRecordDto dangerRecordDto);
    //问题整改
    int problemVerification(DangerRecordDto dangerRecordDto);
}
