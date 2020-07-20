package com.wlhse.dao;

import com.wlhse.dto.FactorHseDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactorHseDao {
    //查询所有
    List<FactorHseDto> getAll();
}
