package com.wlhse.dao;

import com.wlhse.dto.QualityCheckTableRecordDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QualityCheckTableRecordDao {
    Integer batchInsertTree(List<QualityCheckTableRecordDto> QualityCheckLists);
    Integer deleteChickList(Integer qualityCheckID);
    List<QualityCheckTableRecordDto> queryCheckTreeByID(Integer qualityCheckID);

}
