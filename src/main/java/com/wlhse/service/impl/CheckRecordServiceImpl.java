package com.wlhse.service.impl;

import com.wlhse.dao.CheckRecordDao;
import com.wlhse.dto.CheckRecordDto;
import com.wlhse.exception.WLHSException;
import com.wlhse.service.CheckRecordService;
import com.wlhse.util.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CheckRecordServiceImpl implements CheckRecordService {

    @Resource
    private CheckRecordDao checkRecordDao;

    @Override
    public R addCheckRecord(CheckRecordDto checkRecordDto) {
        if ( checkRecordDao.addCheckRecord(checkRecordDto)<= 0)
            throw new WLHSException("新增失败");
        return R.ok();
    }

}
