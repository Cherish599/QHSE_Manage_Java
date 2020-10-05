package com.wlhse.dao;

import com.wlhse.dto.QualityYearManagerDto;

import com.wlhse.dto.QualityYearManagerDtoWithEmployeeId;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ResponseBody
public interface QualityYearManagerDao {
    List<QualityYearManagerDto> queryAll(QualityYearManagerDto qualityYearManagerDto);

    int deleteAll(int id);

    int addCompanyYearManager(QualityYearManagerDtoWithEmployeeId qualityYearManagerDtoWithEmployeeId);
}
