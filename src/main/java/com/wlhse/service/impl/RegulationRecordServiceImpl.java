package com.wlhse.service.impl;

import com.wlhse.dao.RegulationRecordDao;
import com.wlhse.dto.RegulationRecordDto;
import com.wlhse.exception.WLHSException;
import com.wlhse.service.RegulationRecordService;
import com.wlhse.util.R;
import com.wlhse.util.state_code.NR;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class RegulationRecordServiceImpl implements RegulationRecordService {
    @Resource
    private RegulationRecordDao regulationRecordDao;

    @Override
    public R addRegulationRecord(RegulationRecordDto regulationRecordDto) {
        if(regulationRecordDao.addRegulationRecord(regulationRecordDto)<=0)
            throw new WLHSException("新增失败");
        return R.ok();
    }

    @Override
    public R deleteRegulationRecord(Integer id) {
        if(regulationRecordDao.deleteRegulationRecord(id)<=0)
            throw new WLHSException("删除失败");
        return R.ok();
    }

    @Override
    public R updateRegulationRecord(RegulationRecordDto regulationRecordDto) {
        if(regulationRecordDao.updateRegulationRecord(regulationRecordDto)<=0)
            throw new WLHSException("修改失败");
        return R.ok();
    }

    @Override
    public String queryRegulationRecordById(Integer id) {
        List<RegulationRecordDto> list = regulationRecordDao.queryRegulationRecordById(id);
        return NR.r(list);
    }
}