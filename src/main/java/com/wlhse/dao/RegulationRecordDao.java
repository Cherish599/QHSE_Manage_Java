package com.wlhse.dao;


import com.wlhse.dto.RegulationRecordDto;
import com.wlhse.dto.RegulationRecordOutDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface RegulationRecordDao {

    //新增违章记录
    int addRegulationRecord(RegulationRecordDto regulationRecordDto);
    //删除违章记录
    int deleteRegulationRecord(RegulationRecordDto regulationRecordDto);
    //修改违章记录
    int updateRegulationRecord(RegulationRecordDto regulationRecordDto);
    //按ID查询违章记录
    List<RegulationRecordOutDto> queryRegulationRecordById(@Param("id") Integer id);
    //条件查询违章记录
    int queryTotal(RegulationRecordDto regulationRecordDto);
    List<RegulationRecordOutDto> queryRegulationRecord(RegulationRecordDto regulationRecordDto);
}
