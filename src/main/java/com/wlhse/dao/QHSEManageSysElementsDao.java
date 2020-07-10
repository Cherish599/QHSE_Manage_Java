package com.wlhse.dao;

import com.wlhse.dto.outDto.QhseElementsOutDto;
import com.wlhse.entity.QHSECompanySysElementsPojo;
import com.wlhse.entity.QHSEManageSysElements;
import com.wlhse.entity.QhseElementsPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QHSEManageSysElementsDao {
    String querryLastQHSEChildCode(@Param("code") String code);

    Integer addQHSERule(QHSEManageSysElements rule);

    List<QHSECompanySysElementsPojo> querryQHSEReportElements(@Param("status") String status);

    List<QHSECompanySysElementsPojo> querryQHSEReportElements1();

    Integer addTotalCount(@Param("code") String code,@Param("count") Integer count);

    Integer addInitialScore(@Param("code") String code,@Param("score") Integer score);

    Integer updateRule(QHSEManageSysElements rule);

    String querryStatus(String code);

    Integer querryScore(String code);

    Integer setOff(@Param("status") String status,@Param("code") String code);
    Integer setOn(@Param("status") String status,@Param("code") String code);
    Integer getScore(@Param("code") String code);
    //Integer subSoreCount(@Param("code") String code,@Param("score") Integer score);
    Integer sumScore(@Param("code") String code);
    Integer sumCount(@Param("code") String code);
    Integer updateScoreCount(@Param("code") String code,@Param("score") Integer score,@Param("count") Integer count);
    Integer toZero(@Param("code") String code);
    Integer addScoreCount(@Param("code") String code,@Param("score") Integer score);

    //th----查询基本数据表
    List<QhseElementsPojo> queryQhseElements();


}
