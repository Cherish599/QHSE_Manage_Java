package com.wlhse.controller;

import com.wlhse.dto.inDto.CheckListAddDto;
import com.wlhse.dto.inDto.QualityCheckInDto;
import com.wlhse.service.CheckListService;
import com.wlhse.service.QualityCheckListService;
import com.wlhse.util.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("QualityCheckListController")
@RequestMapping("/api/v3")
public class QualityCheckListController {

    @Resource
    private QualityCheckListService qualityCheckListService;

    //查询
    @RequestMapping(value = "/Quality_Check_tree/{tag}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public R getQualityCheckTree(@PathVariable("tag") int tag) {
        return qualityCheckListService.getTreeDto(tag);
    }
    //添加
    @RequestMapping(value = "/addQuality_Check", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public R addQuality_Check(@RequestBody(required = false) QualityCheckInDto qualityCheckInDto) {
        return qualityCheckListService.addQualityCheckNode(qualityCheckInDto);
    }

    //改检查情况，改一级节点下的所有节点
    @RequestMapping(value = "/updateQuality_Check", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public R updateQuality_Check(@RequestBody(required = false)QualityCheckInDto qualityCheckInDto) {
        return qualityCheckListService.updateQualityCheck(qualityCheckInDto);
    }
    //更改状态
    @RequestMapping(value = "/deleteQuality_Check/{id}", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public R deleteQuality_Check(@PathVariable("id") int id) {
        return qualityCheckListService.deleteQualityCheck(id);
    }

}
