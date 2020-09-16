package com.wlhse.service.impl;

import com.wlhse.dao.QualityCheckTableRecordDao;
import com.wlhse.service.QualityCheckTableRecordService;
import com.wlhse.util.R;
import com.wlhse.util.TreeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class QualityCheckTableRecordServiceImpl implements QualityCheckTableRecordService {

    @Resource
    private QualityCheckTableRecordDao qualityCheckTableRecordDao;

    @Resource
    private TreeUtil treeUtil;

    @Override
    public R queryCheckTreeByID(Integer qualityCheckID) {
        R ok = R.ok();
        ok.put("data",treeUtil.getQualityCheckRecordTree(qualityCheckTableRecordDao.queryCheckTreeByID(qualityCheckID)));
        return ok;
    }
}
