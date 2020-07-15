package com.wlhse.controller;


import com.wlhse.dto.FileAuditDto;
import com.wlhse.dto.FileAuditRecordDto;
import com.wlhse.service.FileAuditService;
import com.wlhse.util.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("FileAuditController")
@RequestMapping("/api/v3")
public class FileAuditController {

    @Resource
    private FileAuditService fileAuditService;

    @RequestMapping(value = "/add_fileaduit", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public R addFileAudit(@RequestBody(required = false) FileAuditDto fileAuditDto) {

        return fileAuditService.addFileAudit(fileAuditDto);
    }

    @RequestMapping(value = "/delete_fileaduit/{id}", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
    public R deleteFileAudit(@PathVariable int id) {
        return fileAuditService.deleteFileAudit(id);
    }

    @RequestMapping(value = "/query_fileaduit", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public String queryFileAudit(@ModelAttribute FileAuditDto fileAuditDto) {
        return fileAuditService.queryExistFile(fileAuditDto);
    }

    @RequestMapping(value = "/update_status", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public String updateStatus(@RequestBody(required = false) FileAuditRecordDto fileAuditRecordDto) {
        return fileAuditService.updateStatus(fileAuditRecordDto);
    }

    @RequestMapping(value = "/update_score", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public String updateScore(@RequestBody(required = false) FileAuditRecordDto fileAuditRecordDto) {
        return fileAuditService.updateScore(fileAuditRecordDto);
    }

}
