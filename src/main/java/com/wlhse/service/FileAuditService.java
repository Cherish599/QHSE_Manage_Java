package com.wlhse.service;

import com.wlhse.dto.FileAuditDto;
import com.wlhse.dto.FileAuditRecordDto;
import com.wlhse.dto.inDto.YearElementsDto;
import com.wlhse.util.R;

public interface FileAuditService {

    // 查询
    String queryExistFile(FileAuditDto fileAuditDto);
    // 增加
    R addFileAudit(FileAuditDto fileAuditDto);
    // 删除
    R deleteFileAudit(Integer id);
    //增加文件审核的记录
    R addFileAuditRecord(FileAuditRecordDto fileAuditRecordDto);
    //查询审核记录ID
    Integer queryRecordId(FileAuditRecordDto fileAuditRecordDto);
    //删除文件审核的记录
    R deleteFileAuditRecord(Integer id1);
    //更新文件审核通过与否状态
    String updateStatus(FileAuditRecordDto fileAuditRecordDto);
    //结点打分
    String updateScore(FileAuditRecordDto fileAuditRecordDto);
    //获取结点分数
    String getScore(FileAuditRecordDto fileAuditRecordDto);
    //获取结点审核状态
    String getStatus(FileAuditRecordDto fileAuditRecordDto);
    //更改要素表中的审核状态
    String updateCheckStatus(YearElementsDto yearElementsDto);

}
