package com.wlhse.dao;

import com.wlhse.dto.FileAuditDto;
import com.wlhse.dto.FileAuditRecordDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FileAuditDao {
    //分页+查询存在的记录
    int queryTotal(FileAuditDto fileAuditDto);
    List<FileAuditDto> queryExistFile(FileAuditDto fileAuditDto);

    //新增审核记录
    int addFileAudit(FileAuditDto fileAuditDto);

    //删除审核记录
    int deleteFileAudit(@Param("id") Integer id);

    //更新文件审核通过与否状态
    int updateStatus(FileAuditRecordDto fileAuditRecordDto);

    //对结点打分
    int updateScore(FileAuditRecordDto fileAuditRecordDto);
}
