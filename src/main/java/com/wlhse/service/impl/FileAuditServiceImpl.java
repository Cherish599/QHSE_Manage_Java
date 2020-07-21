package com.wlhse.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlhse.dao.FileAuditDao;
import com.wlhse.dto.FileAuditDto;
import com.wlhse.dto.FileAuditRecordDto;
import com.wlhse.exception.WLHSException;
import com.wlhse.service.FileAuditService;
import com.wlhse.util.R;

import javax.annotation.Resource;
import java.util.List;

import com.wlhse.util.state_code.NR;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileAuditServiceImpl implements FileAuditService {

    @Resource
    private FileAuditDao fileAuditDao;

    @Override
    public String queryExistFile(FileAuditDto fileAuditDto) {
        int total = fileAuditDao.queryTotal(fileAuditDto);
        int pageIdx = fileAuditDto.getPageIdx();
        PageHelper.startPage(pageIdx,fileAuditDto.getPageSize());
        List<FileAuditDto> list = fileAuditDao.queryExistFile(fileAuditDto);
        return NR.r(list, total, pageIdx);
    }

    @Override
    public R addFileAudit(FileAuditDto fileAuditDto) {
        if(fileAuditDao.addFileAudit(fileAuditDto)<=0)
            throw new WLHSException("新增失败");
        return R.ok();
    }

    @Override
    public R deleteFileAudit(Integer id) {
        if(fileAuditDao.deleteFileAudit(id)<=0)
            throw new WLHSException("删除失败");
        return R.ok();
    }

    @Override
    public R addFileAuditRecord(FileAuditRecordDto fileAuditRecordDto) {
        if(fileAuditDao.addFileAuditRecord(fileAuditRecordDto)<=0)
            throw new WLHSException("新增记录失败");
        return R.ok();
    }

    @Override
    public String updateStatus(FileAuditRecordDto fileAuditRecordDto) {
        if(fileAuditDao.updateStatus(fileAuditRecordDto)<=0)
            throw new WLHSException("更新失败");
        return NR.r();
    }

    @Override
    public String updateScore(FileAuditRecordDto fileAuditRecordDto) {
        if(fileAuditDao.updateScore(fileAuditRecordDto)<=0)
            throw new WLHSException("更新失败");
        return NR.r();
    }

    @Override
    public String getScore(FileAuditRecordDto fileAuditRecordDto) {
        List<FileAuditRecordDto> list = fileAuditDao.getScore(fileAuditRecordDto);
        return NR.r(list);
    }
}
