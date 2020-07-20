package com.wlhse.service.impl;

import com.wlhse.dao.FactorSourceDao;
import com.wlhse.dto.FactorSourceDto;
import com.wlhse.service.FactorSourceService;
import com.wlhse.util.state_code.NR;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FactorSourceServiceImpl implements FactorSourceService {

    @Resource
    private FactorSourceDao factorSourceDao;

    @Override
    public String getAll() {
        List<FactorSourceDto> list = factorSourceDao.getAll();
        return NR.r(list);
    }
}
