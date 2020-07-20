package com.wlhse.dao;

import com.wlhse.dto.FactorObserverDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FactorObserverDao {
    //查询所有
    List<FactorObserverDto> getAll();
}
