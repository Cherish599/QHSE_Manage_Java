package com.wlhse.controller;


import com.wlhse.dto.DangerRecordDto;
import com.wlhse.service.DangerRecordService;
import com.wlhse.util.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("DangerRecordController")
@RequestMapping("/api/v3")
public class DangerRecordController {

    @Resource
    private DangerRecordService dangerRecordService;

    @RequestMapping(value = "/add_dangerrecord", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public R addDangerRecord(@RequestBody(required = false) DangerRecordDto dangerRecordDto) {

        return dangerRecordService.addDangerRecord(dangerRecordDto);
    }

    @RequestMapping(value = "/delete_dangerrecord/{id}", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
    public R deleteDangerRecord(@PathVariable int id) {

        return dangerRecordService.deleteDangerRecord(id);
    }

    @RequestMapping(value = "/update_dangerrecord/{id}", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public R updateDangerRecord(@PathVariable int id, @RequestBody(required = false) DangerRecordDto dangerRecordDto) {

        dangerRecordDto.setId(id);
        return dangerRecordService.updateDangerRecord(dangerRecordDto);
    }

    @RequestMapping(value = "/query_dangerrecord/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public String queryDangerRecordById(@PathVariable int id) {

        return dangerRecordService.queryDangerRecordById(id);
    }
}
