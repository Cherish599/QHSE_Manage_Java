package com.wlhse.controller;

import com.wlhse.dto.inDto.CheckListAddDto;
import com.wlhse.dto.outDto.CompanyOutDto;
import com.wlhse.service.CheckListService;
import com.wlhse.service.CompanyService;
import com.wlhse.util.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController("CheckListController")
@RequestMapping("/api/v3")
public class CheckListController {

    @Resource
    private CheckListService checkListService;

    @RequestMapping(value = "/check_list_tree", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public R getCheckListParentAndSon() {
        return checkListService.getTreeDto();
    }

    @RequestMapping(value = "/check_list", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public R addCheckListNode(@RequestBody(required = false)CheckListAddDto checkListAddDto) {
        return checkListService.addCheckListNode(checkListAddDto);
    }

    //改检查情况，改一级节点下的所有节点
    @RequestMapping(value = "/check_list/{id}", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public R updateCheckList(@PathVariable int id,@RequestBody(required = false)CheckListAddDto checkListAddDto) {
        return checkListService.updateCheckList(id,checkListAddDto);
    }

    @RequestMapping(value = "/check_list/{id}", method = RequestMethod.DELETE, produces = "application/json; charset=utf-8")
    public R deleteCheckList(@PathVariable int id) {
        return checkListService.deleteCheckList(id);
    }

}
