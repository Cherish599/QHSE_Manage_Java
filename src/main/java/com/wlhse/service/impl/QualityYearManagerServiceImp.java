package com.wlhse.service.impl;

import com.wlhse.cache.JedisClient;
import com.wlhse.dao.QualityYearManagerDao;
import com.wlhse.dto.CompanyYearManagerDtoWithEmployeeId;
import com.wlhse.dto.QualityYearManagerDto;
import com.wlhse.dto.QualityYearManagerDtoWithEmployeeId;
import com.wlhse.exception.WLHSException;
import com.wlhse.service.QualityYearManagerService;
import com.wlhse.util.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QualityYearManagerServiceImp implements QualityYearManagerService {
    @Resource
    JedisClient jedisClient;
    @Resource
    QualityYearManagerDao qualityYearManagerDao;
    @Override
    public R queryAll(QualityYearManagerDtoWithEmployeeId companyYearManagerDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Map<String, String> map1 = jedisClient.hGetAll(token);
        int employeeId = Integer.valueOf(map1.get("employeeId"));
        companyYearManagerDto.setEmployeeId(employeeId);
        List<QualityYearManagerDto> pojo=qualityYearManagerDao.queryAll(companyYearManagerDto);
        Map<String, Object> map = new HashMap<>();
        map.put("data", pojo);
        return R.ok(map);
    }

    @Override
    public R deleteALL(int id) {
        //Should use transactions to delete data from those tables.Ensure the atomicity of database transactions.
        try {
            qualityYearManagerDao.deleteAll(id);
        }
        catch (Exception e) {
            throw new WLHSException("删除失败");
        }
        return R.ok();
    }



    @Override
    public R addCompanyYearManager(QualityYearManagerDtoWithEmployeeId companyYearManagerDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Map<String, String> map1 = jedisClient.hGetAll(token);
        int employeeId = Integer.valueOf(map1.get("employeeId"));
        companyYearManagerDto.setEmployeeId(employeeId);
        if(qualityYearManagerDao.addCompanyYearManager(companyYearManagerDto)<=0)
            throw new WLHSException("新增失败");
        return R.ok();
    }
}
