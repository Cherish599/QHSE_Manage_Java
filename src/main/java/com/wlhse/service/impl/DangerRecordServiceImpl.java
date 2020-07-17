package com.wlhse.service.impl;

import com.wlhse.dao.DangerRecordDao;
import com.wlhse.dto.DangerRecordDto;
import com.wlhse.exception.WLHSException;
import com.wlhse.service.DangerRecordService;
import com.wlhse.util.R;
import com.wlhse.util.state_code.NR;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DangerRecordServiceImpl implements DangerRecordService {

    @Resource
    private DangerRecordDao dangerRecordDao;

    @Override
    public R addDangerRecord(DangerRecordDto dangerRecordDto) {
        if(dangerRecordDao.addDangerRecord(dangerRecordDto)<=0)
            throw new WLHSException("新增失败");
        return R.ok();
    }

    @Override
    public R deleteDangerRecord(Integer id) {
        if(dangerRecordDao.deleteDangerRecord(id)<=0)
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
}
