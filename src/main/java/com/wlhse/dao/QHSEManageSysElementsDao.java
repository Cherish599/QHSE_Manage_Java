package com.wlhse.dao;

import com.wlhse.dto.inDto.QSHEMSElementInDto;
import com.wlhse.dto.inDto.YearElementsDto;
import com.wlhse.dto.outDto.ElementAndConfigStatusDto;
import com.wlhse.dto.outDto.QhseElementsOutDto;
import com.wlhse.entity.QHSECompanySysElementsPojo;
import com.wlhse.entity.QHSEManageSysElements;
import com.wlhse.entity.QhseElementsPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QHSEManageSysElementsDao {
    String querryLastQHSEChildCode(@Param("code") String code);
    String querryLastQHSEChildCode2(@Param("code") String code);//查询最后一个字节的重写

    Integer addQHSERule(QHSEManageSysElements rule);
    Integer addQHSEElement(QhseElementsPojo element);//换类型重写
    Integer addExcelQHSEElement(QSHEMSElementInDto element);//excel文件添加节点


    List<QHSECompanySysElementsPojo> querryQHSEReportElements(@Param("status") String status);

    List<QHSECompanySysElementsPojo> querryQHSEReportElements1();

    Integer addTotalCount(@Param("code") String code,@Param("count") Integer count);

    Integer addInitialScore(@Param("code") String code,@Param("score") Integer score);

    Integer updateRule(QHSEManageSysElements rule);
    Integer updateElement(QhseElementsPojo rule);//换类型重写
    Integer updateExcelElement(QSHEMSElementInDto element);//excel文件更新内容

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

    //th----查询基本数据表，仅启用
    List<QhseElementsPojo> queryQhseElements();
    //查询年度要素表
    List<YearElementsDto> queryQhseYearElements(YearElementsDto yearElementsDto);

    //th----查询基本数据表两级
    List<QhseElementsPojo> queryQhseChildElements();
    //th----查询所有的数据表
    List<QhseElementsPojo> queryQhseAllElements();
    //查询编码是否存在
    String querryCode(@Param("code") String code);


    List<YearElementsDto> queryElementsByCode(String c);
    YearElementsDto queryElementByCode(String code);
    Integer findMaxLen();

    Integer addYearElement(YearElementsDto yearElementsDto);

    //查找指定tableid
    List<YearElementsDto> queryByTableID(@Param("id") Integer id);

    int deleteByTableID(@Param("id") Integer id);

    List<ElementAndConfigStatusDto> selectCodeAndConfigStatusByTableId(int tableId);
    int updateConfigStatus(String code,int tableId,String status);
}
