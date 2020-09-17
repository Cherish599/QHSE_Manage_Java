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
    List<QualityCheckDto> queryTableByDate(String company,String beginDate,String endDate);
    Integer pushTable(Integer qualityCheckID);
    List<QualityCheckDto> queryTableByDateAndPush(String company,String beginDate,String endDate);
}
