package com.wlhse.service;

import com.wlhse.dto.FileAuditDto;
import com.wlhse.dto.FileAuditRecordDto;
import com.wlhse.util.R;

public interface FileAuditService {

    // 查询
    String queryExistFile(FileAuditDto fileAuditDto);
    // 增加
    R addFileAudit(FileAuditDto fileAuditDto);
    // 删除
    R deleteFileAudit(Integer id);
    //更新文件审核通过与否状态
    String updateStatus(FileAuditRecordDto fileAuditRecordDto);
    //结点打分
    String updateScore(FileAuditRecordDto fileAuditRecordDto);
}
