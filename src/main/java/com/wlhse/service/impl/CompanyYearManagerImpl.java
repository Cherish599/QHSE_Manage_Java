package com.wlhse.service.impl;

import com.wlhse.dao.CompanyYearManagerDao;
import com.wlhse.dto.inDto.CompanyYearManagerDto;
import com.wlhse.exception.WLHSException;
import com.wlhse.service.CompanyYearManagerService;
import com.wlhse.util.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompanyYearManagerImpl implements CompanyYearManagerService {
    @Resource
    private CompanyYearManagerDao companyYearManagerDao;

    @Override
    public R updateStatus(int id) {
        if(companyYearManagerDao.updateAll(id)<=0)
            throw new WLHSException("更新失败");
        return R.ok();
    }

    @Override
    public R queryAll(CompanyYearManagerDto companyYearManagerDto) {
       List<CompanyYearManagerDto> pojo=companyYearManagerDao.queryAll(companyYearManagerDto);
        Map<String, Object> map = new HashMap<>();
        map.put("data", pojo);
        return R.ok(map);
    }

    @Override
    public R deleteALL(int id) {
        if(companyYearManagerDao.deleteAll(id)<=0)
            throw new WLHSException("删除失败");
        return R.ok();
    }
}
