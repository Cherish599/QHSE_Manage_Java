package com.wlhse.controller;


import com.wlhse.dto.QualityFileAuditDto;
import com.wlhse.dto.QualityFileAuditRecordDto;
import com.wlhse.dto.inDto.QualityYearElementsDto;
import com.wlhse.dto.inDto.YearElementsDto;
import com.wlhse.service.QHSEManageSysElementsService;
import com.wlhse.service.QualityFileAuditService;
import com.wlhse.util.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController("QualityFileAuditController")
@RequestMapping("/api/v3")
public class QualityFileAuditController {

    @Resource
    private QualityFileAuditService qualityFileAuditService;
    @Resource
    QHSEManageSysElementsService qhseManageSysElementsService;


//    @RequestMapping(value = "/getFileAuditProgress",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
//    public R getProgress(@RequestParam(value = "tableId",required = false)Integer tableId){
//        return qhseManageSysElementsService.getTableCheckedProgress(tableId);
//    }

    @RequestMapping(value = "/add_qualityfileaduit", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public R addFileAudit(@RequestBody(required = false) QualityFileAuditDto qualityFileAuditDto) {
        return qualityFileAuditService.addFileAudit(qualityFileAuditDto);
    }

    @RequestMapping(value = "/delete_qualityfileaduit/{id}", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
    public R deleteFileAudit(@PathVariable int id) {
        return qualityFileAuditService.deleteFileAudit(id);
    }

    @RequestMapping(value = "/query_qualityfileaduit", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public String queryFileAudit(@ModelAttribute QualityFileAuditDto qualityFileAuditDto) {
        return qualityFileAuditService.queryExistFile(qualityFileAuditDto);
    }

    @RequestMapping(value = "/quality_update_status", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public String updateStatus(@RequestBody(required = false) QualityFileAuditRecordDto qualityFileAuditRecordDto) {
        return qualityFileAuditService.updateStatus(qualityFileAuditRecordDto);
    }

    @RequestMapping(value = "/quality_update_score", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public String updateScore(@RequestBody(required = false) QualityFileAuditRecordDto qualityFileAuditRecordDto) {
        return qualityFileAuditService.updateScore(qualityFileAuditRecordDto);
    }

    @RequestMapping(value = "/quality_get_score", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public String getScore(@ModelAttribute QualityFileAuditRecordDto qualityFileAuditRecordDto) {
        return qualityFileAuditService.getScore(qualityFileAuditRecordDto);
    }

    @RequestMapping(value = "/add_qualityfileaduitrecord", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public R addFileAuditRecord(@RequestBody(required = false) QualityFileAuditRecordDto qualityFileAuditRecordDto) {
        return qualityFileAuditService.addFileAuditRecord(qualityFileAuditRecordDto);
    }

    @RequestMapping(value = "/queryqualityrecordId", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public String queryRecordId(@ModelAttribute QualityFileAuditRecordDto qualityFileAuditRecordDto) {
        return qualityFileAuditService.queryRecordId(qualityFileAuditRecordDto);
    }

    @RequestMapping(value = "/delete_qualityfileaduitrecord/{id1}", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
    public R deleteFileAuditRecord(@PathVariable int id1) {
        return qualityFileAuditService.deleteFileAuditRecord(id1);
    }


    @RequestMapping(value = "/quality_get_status", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public String getStatus(@ModelAttribute QualityFileAuditRecordDto qualityFileAuditRecordDto) {
        return qualityFileAuditService.getStatus(qualityFileAuditRecordDto);
    }

    // tobing
    @RequestMapping(value = "/update_qualitycheckstatus", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public String updateCheckStatus(@RequestBody(required = false) QualityYearElementsDto yearElementsDto) {
        return qualityFileAuditService.updateCheckStatus(yearElementsDto);
    }
}
