package com.wlhse.controller;

import com.wlhse.dto.QualityCheckTableRecordDto;
import com.wlhse.service.QualityCheckTableRecordService;
import com.wlhse.util.R;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/addQualityInform", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public R addQualityInform(@RequestBody(required = false) QualityCheckTableRecordDto qualityCheckTableRecordDto) {
        return qualityCheckTableRecordService.addInformAndAttach(qualityCheckTableRecordDto);
    }


}
