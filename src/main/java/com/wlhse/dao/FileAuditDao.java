package com.wlhse.dao;

import com.wlhse.dto.FileAuditDto;
import com.wlhse.dto.FileAuditRecordDto;
import com.wlhse.dto.inDto.YearElementsDto;
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

    //增加审核记录
    int addFileAuditRecord(FileAuditRecordDto fileAuditRecordDto);
    //查询审核记录的ID
    int queryRecordId(FileAuditRecordDto fileAuditRecordDto);

    //删除审核历史记录
    int deleteFileAuditRecord(@Param("id1") Integer id1);

    //更新文件审核通过与否状态
    int updateStatus(FileAuditRecordDto fileAuditRecordDto);

    //对结点打分
    int updateScore(FileAuditRecordDto fileAuditRecordDto);

    //获取结点分数
    List<FileAuditRecordDto> getScore(FileAuditRecordDto fileAuditRecordDto);

    //获取结点审核状态
    List<FileAuditRecordDto> getStatus(FileAuditRecordDto fileAuditRecordDto);

    //更改要素表中的审核状态
    int updateCheckStatus(YearElementsDto yearElementsDto);
}
