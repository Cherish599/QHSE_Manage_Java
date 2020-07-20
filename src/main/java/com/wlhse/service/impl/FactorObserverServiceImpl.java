package com.wlhse.service.impl;

import com.wlhse.dao.FactorObserverDao;
import com.wlhse.dto.FactorObserverDto;
import com.wlhse.service.FactorObserverService;
import com.wlhse.util.state_code.NR;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FactorObserverServiceImpl implements FactorObserverService {

    @Resource
    private FactorObserverDao factorObserverDao;

    @Override
    public String getAll() {
        List<FactorObserverDto> list = factorObserverDao.getAll();
        return NR.r(list);
    }
}
