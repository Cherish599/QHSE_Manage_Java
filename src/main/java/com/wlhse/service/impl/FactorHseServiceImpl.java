package com.wlhse.service.impl;

import com.wlhse.dao.FactorHseDao;
import com.wlhse.dto.FactorHseDto;
import com.wlhse.service.FactorHseService;
import com.wlhse.util.state_code.NR;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FactorHseServiceImpl implements FactorHseService {

    @Resource
    private  FactorHseDao factorHseDao;

    @Override
    public String getAll() {
        List<FactorHseDto> list = factorHseDao.getAll();
        return NR.r(list);
    }
}
