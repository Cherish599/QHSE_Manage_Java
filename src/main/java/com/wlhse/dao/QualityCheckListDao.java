package com.wlhse.dao;

import com.wlhse.dto.CheckListDto;
import com.wlhse.dto.QualityCheckListDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QualityCheckListDao {
    List<QualityCheckListDto> getTreeDto();
    List<QualityCheckListDto> getAllTreeDto();
    List<String> getAllTableNode();
    List<String> getCurrentChildNodes(@Param("code") String code);
    QualityCheckListDto getCheckListOne(@Param("code") String code);
    int updateIsChild(@Param("code") String code, @Param("status") String status);
    Integer addCheckList(QualityCheckListDto qualityCheckListDto);
    List<QualityCheckListDto> getCurrentAllChild(@Param("code") String code);
    int updateCheckList(QualityCheckListDto qualityCheckListDto);
    QualityCheckListDto getById(@Param("id") int id);
    int updateAllChildStatus(@Param("code") String code);
    int updateStatus(@Param("status") String status,@Param("code")String code);


}
