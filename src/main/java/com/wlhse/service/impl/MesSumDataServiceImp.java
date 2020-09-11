package com.wlhse.service.impl;

import com.wlhse.dao.MesSumDataDao;
import com.wlhse.service.MesSumDataService;
import com.wlhse.util.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MesSumDataServiceImp implements MesSumDataService {

    @Resource
    MesSumDataDao mesSumDataDao;

    @Override
    public R getAllSumDate() {
        R r=new R();
        r.put("data",mesSumDataDao.getAllSumDate());
        return r;
    }

    @Override
    public R getMesCheckDataByDate(String date) {
        R r=new R();
        r.put("data",mesSumDataDao.getSumDataByDate(date));
        return r;
    }

    @Override
    public R deleteSumData(int id) {
        mesSumDataDao.deleteSumData(id);
        return R.ok();
    }
}
