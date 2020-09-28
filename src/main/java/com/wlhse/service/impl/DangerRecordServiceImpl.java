package com.wlhse.service.impl;

import com.github.pagehelper.PageHelper;
import com.wlhse.cache.JedisClient;
import com.wlhse.dao.DangerRecordDao;
import com.wlhse.dto.DangerRecordDto;
import com.wlhse.exception.WLHSException;
import com.wlhse.service.DangerRecordService;
import com.wlhse.service.EmployeeManagementService;
import com.wlhse.util.R;
import com.wlhse.util.state_code.NR;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class DangerRecordServiceImpl implements DangerRecordService {

    @Resource
    private DangerRecordDao dangerRecordDao;
    @Resource
    JedisClient jedisClient;
    @Resource
    EmployeeManagementService employeeManagementService;
    @Override
    public R addDangerRecord(DangerRecordDto dangerRecordDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Map<String, String> map = jedisClient.hGetAll(token);
        int employeeId = Integer.valueOf(map.get("employeeId"));
        dangerRecordDto.setSafeStaff_ID(employeeId);
        String employeeName = employeeManagementService.getEmployeeNameByEmployeeID(employeeId);
        dangerRecordDto.setSafeStaff_Name(employeeName);
        if(dangerRecordDao.addDangerRecord(dangerRecordDto)<=0)
            throw new WLHSException("新增失败");
        return R.ok();
    }

    @Override
    public R deleteDangerRecord(DangerRecordDto dangerRecordDto) {
        if(dangerRecordDao.deleteDangerRecord(dangerRecordDto)<=0)
            throw new WLHSException("删除失败");
        return R.ok();
    }

    @Override
    public R updateDangerRecord(DangerRecordDto dangerRecordDto) {
        if(dangerRecordDao.updateDangerRecord(dangerRecordDto)<=0)
            throw new WLHSException("修改失败");
        return R.ok();
    }

    @Override
    public String queryDangerRecordById(Integer id) {
        List<DangerRecordDto> list = dangerRecordDao.queryDangerRecordById(id);
        return NR.r(list);
    }

    @Override
    public String queryDangerRecord(DangerRecordDto dangerRecordDto) {
        int total = dangerRecordDao.queryTotal(dangerRecordDto);
        int pageIdx = dangerRecordDto.getPageIdx();
        PageHelper.startPage(pageIdx, dangerRecordDto.getPageSize());
        List<DangerRecordDto> list = dangerRecordDao.queryDangerRecord(dangerRecordDto);
        return NR.r(list, total, pageIdx);
    }

    @Override
    public R problemVerification(DangerRecordDto dangerRecordDto) {
        if(dangerRecordDao.problemVerification(dangerRecordDto)<=0)
            throw new WLHSException("修改失败");
        return R.ok();
    }


}
