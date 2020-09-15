package com.wlhse.controller;

import com.wlhse.dto.QualityCheckDto;
import com.wlhse.dto.inDto.QualityCheckInDto;
import com.wlhse.service.QualityCheckService;
import com.wlhse.util.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("QualityCheckController")
@RequestMapping("/api/v3")
public class QualityCheckController {

    @Resource
    private QualityCheckService qualityCheckService;

    //查询
    @RequestMapping(value = "/queryAllTable", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public R queryAllTable() {
        return qualityCheckService.queryAllTable();
    }

    //查询
    @RequestMapping(value = "/queryTableByYearAndCom", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public R queryTableByYearAndCom(@RequestBody(required = false) QualityCheckDto qualityCheckDto) {
        return qualityCheckService.queryTableByYearAndCom(qualityCheckDto);
    }
    //添加
    @RequestMapping(value = "/addQualityCheck", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public R addQualityCheck(@RequestBody(required = false) QualityCheckDto qualityCheckDto) {
        return qualityCheckService.addQualityCheck(qualityCheckDto);
    }

    //改
    @RequestMapping(value = "/updateQualityCheck", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public R updateQualityCheck(@RequestBody(required = false)QualityCheckDto qualityCheckDto) {
        return qualityCheckService.updateQualityCheck(qualityCheckDto);
    }
    //
    @RequestMapping(value = "/deleteQualityCheck/{id}", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
    public R deleteQuality_Check(@PathVariable("id") int id) {
        return qualityCheckService.deleteQualityCheck(id);
    }

    @RequestMapping(value = "/pushTable/{id}", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public R pushTable(@PathVariable("id") int id) {
        return qualityCheckService.pushTable(id);
    }



}
