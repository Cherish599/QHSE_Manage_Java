package com.wlhse.dao;

import com.wlhse.dto.QualityCheckDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QualityCheckDao {
    Integer addQualityCheck(QualityCheckDto qualityCheckDto);
    Integer deleteQualityCheck(Integer qualityCheckID);
    Integer updateQualityCheck(QualityCheckDto qualityCheckDto);
    String queryCheckListCodeById(Integer id);//code 可能不只一个，用';'分隔
    List<QualityCheckDto> queryAllTable();
    List<QualityCheckDto> queryTableByDate(QualityCheckDto qualityCheckDto);
    Integer pushTable(Integer qualityCheckID);
}
