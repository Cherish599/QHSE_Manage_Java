package com.wlhse.service.impl;

import com.wlhse.dao.QulityCheckRecordDao;
import com.wlhse.entity.QulityCheckRecordPojo;
import com.wlhse.service.QulityCheckRecordService;
import com.wlhse.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tobing
 */
@Service
public class QulityCheckRecordServiceImpl implements QulityCheckRecordService {

    @Autowired
    private QulityCheckRecordDao qulityCheckRecordDao;

    @Override
    public R addQulityCheckRecord(QulityCheckRecordPojo qulityCheckRecordPojo) {
        // 判断记录合法性
        if (qulityCheckRecordPojo == null) {
            return R.error("插入数据为空");
        }

        // 判断基础表数据是否为空
        if (qulityCheckRecordPojo.getQulity_CheckID() == null
                || qulityCheckRecordPojo.getCheckListCode() == null
                || qulityCheckRecordPojo.getNo() == null) {
            return R.error("参数不合法");
        }

        // 问题分类：不符合
        // 预处理违章项相关字段
        if ("不符合".equals(qulityCheckRecordPojo.getNature())) {
            qulityCheckRecordPojo.setNonConformCorrectMeasureVerify(null);
            qulityCheckRecordPojo.setNonConformCorrectMeasure(null);
            qulityCheckRecordPojo.setNonConformCorrect(null);
            qulityCheckRecordPojo.setNonConformSource(null);
            qulityCheckRecordPojo.setNonConformClauseContent(null);
            qulityCheckRecordPojo.setNonConformClauseNo(null);
            qulityCheckRecordPojo.setNonConformityStd(null);
            qulityCheckRecordPojo.setNonConformityType(null);
            qulityCheckRecordPojo.setNonConformityNature(null);
        }

        // 问题分类：违章项
        // 预处理不符合相关字段
        if ("违章项".equals(qulityCheckRecordPojo.getNature())) {
            qulityCheckRecordPojo.setViolationClauseContent(null);
            qulityCheckRecordPojo.setViolationDeduction(null);
            qulityCheckRecordPojo.setViolationScore(null);
            qulityCheckRecordPojo.setViolationClause(null);
        }

        try {
            qulityCheckRecordDao.addQualityCheckRecord(qulityCheckRecordPojo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok("插入成功");
    }

    @Override
    public R queryQulityCheckRecord() {
        List<QulityCheckRecordPojo> qulityCheckRecordPojos = qulityCheckRecordDao.queryQualityCheckRecord();
        // 判断结果合法性
        if (qulityCheckRecordPojos == null || qulityCheckRecordPojos.size() == 0) {
            return R.error("No Result");
        }
        // 保存查询结果集
        HashMap<String, Object> res = new HashMap<>();
        res.put("data", qulityCheckRecordPojos);
        return R.ok(res);
    }

    @Override
    public R deleteQulityCheckRecord(Integer id) {
        qulityCheckRecordDao.deleteQualityCheckRecord(id);
        return R.ok("删除成功");
    }

    @Override
    public R updateQulityCheckRecord(Integer id, QulityCheckRecordPojo qulityCheckRecordPojo) {
        if (id == null || qulityCheckRecordPojo == null) {
            return R.error("参数不合法");
        }
        qulityCheckRecordPojo.setQulity_CheckRecordID(id);
        try {
            qulityCheckRecordDao.updateQualityCheckRecord(qulityCheckRecordPojo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok("更新成功");
    }

    @Override
    public R queryQulityCheckRecordByCheckId(String checkId) {
        if (checkId == null) {
            return R.error("参数不合法");
        }
        List<QulityCheckRecordPojo> recordPojoList = qulityCheckRecordDao.queryQulityCheckRecordByCheckId(checkId);
        Map<String, Object> res = new HashMap<>();
        res.put("data", recordPojoList);
        return R.ok(res);
    }
}
