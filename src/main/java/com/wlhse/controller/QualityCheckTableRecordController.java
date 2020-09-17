package com.wlhse.controller;

import com.wlhse.service.QualityCheckTableRecordService;
import com.wlhse.util.R;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("QualityCheckTableRecordController")
@RequestMapping("/api/v3")
public class QualityCheckTableRecordController {
    @Resource
    private QualityCheckTableRecordService qualityCheckTableRecordService;

    @RequestMapping(value = "/queryCheckTreeByID/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public R queryAllTable(@PathVariable("id") int id) {
        return qualityCheckTableRecordService.queryCheckTreeByID(id);
    }
}
