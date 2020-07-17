package com.wlhse.controller;


import com.wlhse.dto.RegulationRecordDto;
import com.wlhse.service.RegulationRecordService;
import com.wlhse.util.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("RegulationRecordController")
@RequestMapping("/api/v3")
public class RegulationRecordController {

    @Resource
    private RegulationRecordService regulationRecordService;

    @RequestMapping(value = "/add_regulationrecord", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public R addDangerRecord(@RequestBody(required = false) RegulationRecordDto regulationRecordDto) {

        return regulationRecordService.addRegulationRecord(regulationRecordDto);
    }

    @RequestMapping(value = "/delete_regulationrecord/{id}", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
    public R deleteRegulationRecord(@PathVariable int id) {

        return regulationRecordService.deleteRegulationRecord(id);
    }

    @RequestMapping(value = "/update_regulationrecord/{id}", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public R updateRegulationRecord(@PathVariable int id, @RequestBody(required = false) RegulationRecordDto regulationRecordDto) {

        regulationRecordDto.setId(id);
        return regulationRecordService.updateRegulationRecord(regulationRecordDto);
    }

    @RequestMapping(value = "/query_regulationrecord/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public String queryRegulationRecordById(@PathVariable int id) {

        return regulationRecordService.queryRegulationRecordById(id);
    }

}