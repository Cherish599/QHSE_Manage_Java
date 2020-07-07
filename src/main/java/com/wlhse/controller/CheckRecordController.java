package com.wlhse.controller;

import com.wlhse.dto.CheckRecordDto;
import com.wlhse.service.CheckRecordService;
import com.wlhse.util.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController("CheckRecordController")
@RequestMapping("/api/v3")
public class CheckRecordController {

    @Resource
    private CheckRecordService checkRecordService;


    @RequestMapping(value = "/check_record", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public R addCheckRecord(@RequestBody(required = false) CheckRecordDto checkRecordDto) {
        return checkRecordService.addCheckRecord(checkRecordDto);
    }


}
