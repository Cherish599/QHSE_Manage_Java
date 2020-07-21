package com.wlhse.service.impl;

import com.github.pagehelper.PageHelper;
import com.wlhse.dao.FactorDepartmentDao;
import com.wlhse.dao.QHSEAccidentDao;
import com.wlhse.dto.QHSEAccidentDto;
import com.wlhse.exception.WLHSException;
import com.wlhse.service.AccidentService;
import com.wlhse.service.FactorDepartmentService;
import com.wlhse.util.R;
import com.wlhse.util.state_code.NR;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FactorDepartmentServiceImpl implements FactorDepartmentService {
    @Resource
    private FactorDepartmentDao factorDepartmentDao;

    @Override
    public R queryFactorDepartment() {
        return R.ok().put("data",factorDepartmentDao.findAll());
    }

}
