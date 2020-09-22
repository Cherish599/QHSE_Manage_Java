package com.wlhse.controller;

import com.wlhse.dto.QualityCheckDto;
import com.wlhse.dto.inDto.QualityCheckInDto;
import com.wlhse.service.QualityCheckService;
import com.wlhse.util.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("QualityCheckController")
@RequestMapping("/api/v3")
public class QualityCheckController {

    @Resource
    private QualityCheckService qualityCheckService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //查询所有
    @RequestMapping(value = "/queryAllTable", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public R queryAllTable() {
        return qualityCheckService.queryAllTable();
    }

    //查询+条件
    @RequestMapping(value = "/queryTableByYearAndCom", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public R queryTableByYearAndCom(@ModelAttribute QualityCheckDto qualityCheckDto) {
        logger.info("传入的数据:"+qualityCheckDto.toString());
        return qualityCheckService.queryTableByYearAndCom(qualityCheckDto);
    }
    //查询已推送+条件
    @RequestMapping(value = "/queryTableByYearAndComAndPush", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public R queryTableByYearAndComAndPush(@ModelAttribute QualityCheckDto qualityCheckDto) {
        logger.info("传入的数据:"+qualityCheckDto.toString());
        return qualityCheckService.queryTableByYearAndComAndPush(qualityCheckDto);
    }
    //添加
    @RequestMapping(value = "/addQualityCheck", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public R addQualityCheck(@RequestBody(required = false) QualityCheckDto qualityCheckDto) {
        logger.info("传入的数据:"+qualityCheckDto.toString());
        return qualityCheckService.addQualityCheck(qualityCheckDto);
    }

    //改
    @RequestMapping(value = "/updateQualityCheck/{id}", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public R updateQualityCheck(@PathVariable("id") int id,@RequestBody(required = false)QualityCheckDto qualityCheckDto) {
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
