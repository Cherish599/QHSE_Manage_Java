package com.wlhse.service.impl;

import com.wlhse.dao.QualityCheckDao;
import com.wlhse.dao.QualityCheckListDao;
import com.wlhse.dao.QualityCheckTableRecordDao;
import com.wlhse.dto.QualityCheckDto;
import com.wlhse.dto.QualityCheckTableRecordDto;
import com.wlhse.exception.WLHSException;
import com.wlhse.service.QualityCheckService;
import com.wlhse.util.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class QualityCheckServiceImpl implements QualityCheckService {

    @Resource
    private QualityCheckDao qualityCheckDao;

    @Resource
    private QualityCheckListDao qualityCheckListDao;

    @Resource
    private QualityCheckTableRecordDao qualityCheckTableRecordDao;

    @Transactional
    @Override
    public R addQualityCheck(QualityCheckDto qualityCheckDto) {
        qualityCheckDto.setIsPush("未推送");
        if(qualityCheckDao.addQualityCheck(qualityCheckDto)<0)
            throw new WLHSException("插入失败");
        List<QualityCheckTableRecordDto> allQualityCheckTableRecord=getCheckTree(qualityCheckDto.getQualityCheckID(),qualityCheckDto.getCheckListCode());
        if(allQualityCheckTableRecord.size()>0){
            if(qualityCheckTableRecordDao.batchInsertTree(allQualityCheckTableRecord)<0)
                throw new WLHSException("插入失败");
        }
        return R.ok();
    }

    @Override
    public R deleteQualityCheck(Integer qualityCheckID) {
        if(qualityCheckDao.deleteQualityCheck(qualityCheckID)<0)//设置了级联删除，删除该表，后面的检查表也会删除
            throw new WLHSException("删除失败");
        return R.ok();
    }

    @Transactional
    @Override
    public R updateQualityCheck(QualityCheckDto qualityCheckDto) {
        Integer qualityCheckID=qualityCheckDto.getQualityCheckID();
        String newCheckListCode=qualityCheckDto.getCheckListCode();
        String oldCheckListCode=qualityCheckDao.queryCheckListCodeById(qualityCheckID);
        //内容更新
        if(qualityCheckDao.updateQualityCheck(qualityCheckDto)<0)
            throw new WLHSException("更新失败");
        //更新后面的QualityCheckTableRecord表,有变化才更新
        if(!newCheckListCode.equals(oldCheckListCode)){//直接删除原数据，重新写入。
            qualityCheckTableRecordDao.deleteChickList(qualityCheckID);
            List<QualityCheckTableRecordDto> allQualityCheckTableRecord=getCheckTree(qualityCheckID,newCheckListCode);
            if(allQualityCheckTableRecord.size()>0){
                if(qualityCheckTableRecordDao.batchInsertTree(allQualityCheckTableRecord)<0)
                    throw new WLHSException("插入失败");
            }
        }
        return R.ok();
    }

    @Override
    public R queryAllTable() {
        R ok = R.ok();
        ok.put("data",qualityCheckDao.queryAllTable());
        return ok;
    }

    @Override
    public R queryTableByYearAndCom(QualityCheckDto qualityCheckDto) {
        R ok = R.ok();
        ok.put("data",qualityCheckDao.queryTableByDate(qualityCheckDto));
        return ok;
    }

    @Override
    public R pushTable(Integer qualityCheckID) {
       if(qualityCheckDao.pushTable(qualityCheckID)<0)
           throw new WLHSException("更新失败");
        return R.ok();
    }

    public List<QualityCheckTableRecordDto> getCheckTree(Integer qualityCheckID,String checkCode)
    {
        String[] checkListCodes =checkCode.split(";");
        List<QualityCheckTableRecordDto> allQualityCheckTableRecord=new ArrayList<>();
        for(String code: checkListCodes){
            List<QualityCheckTableRecordDto> QualityCheckTableRecords=qualityCheckListDao.findTreeByCode(code);
            for(QualityCheckTableRecordDto qualityCheckTableRecordDto:QualityCheckTableRecords){
                qualityCheckTableRecordDto.setQualityCheckID(qualityCheckID);
            }
            allQualityCheckTableRecord.addAll(QualityCheckTableRecords);
        }
        return allQualityCheckTableRecord;
    }

}
