package com.wlhse.service;

import com.wlhse.dto.QualityFileAuditDto;
import com.wlhse.dto.QualityFileAuditRecordDto;
import com.wlhse.dto.inDto.YearElementsDto;
import com.wlhse.util.R;

public interface QualityFileAuditService {

    // 查询
    String queryExistFile(QualityFileAuditDto qualityFileAuditDto);
    // 增加
    R addFileAudit(QualityFileAuditDto qualityFileAuditDto);
    // 删除
    R deleteFileAudit(Integer id);
    //增加文件审核的记录
    R addFileAuditRecord(QualityFileAuditRecordDto qualityFileAuditRecordDto);
    //查询审核记录ID
    String queryRecordId(QualityFileAuditRecordDto qualityFileAuditRecordDto);
    //删除文件审核的记录
    R deleteFileAuditRecord(Integer id1);
    //更新文件审核通过与否状态
    String updateStatus(QualityFileAuditRecordDto qualityFileAuditRecordDto);
    //结点打分
    String updateScore(QualityFileAuditRecordDto qualityFileAuditRecordDto);
    //获取结点分数
    String getScore(QualityFileAuditRecordDto qualityFileAuditRecordDto);
    //获取结点审核状态
    String getStatus(QualityFileAuditRecordDto qualityFileAuditRecordDto);
    //更改要素表中的审核状态
    // TODO 表没改
    String updateCheckStatus(YearElementsDto yearElementsDto);

}
