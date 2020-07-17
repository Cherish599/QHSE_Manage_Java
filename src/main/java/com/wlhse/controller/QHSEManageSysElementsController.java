package com.wlhse.controller;


import com.wlhse.dto.inDto.YearElementsDto;
import com.wlhse.entity.QHSEManageSysElements;
import com.wlhse.entity.QhseElementsPojo;
import com.wlhse.service.QHSEManageSysElementsService;
import com.wlhse.util.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("QHSEManageSysElementsController")
@RequestMapping("/api/v3")
public class QHSEManageSysElementsController {

    @Resource
    private QHSEManageSysElementsService qhseManageSysElementsService;


    //th---查询QHSE_ManagerSysElement基本表
    @RequestMapping(value = "/querryQhseElement", method = RequestMethod.GET, produces = {"application/json;charset=utf-8"})
    public R querryQhseManagerSysElement(){
        return qhseManageSysElementsService.queryAllElement();
    }

    //th---查询QHSE_ManagerSysElement基本表两级
    @RequestMapping(value = "/querryQhseChildElement", method = RequestMethod.GET, produces = {"application/json;charset=utf-8"})
    public R querryQhseManagerSysChildElement(){
        return qhseManageSysElementsService.queryChildElement();
    }
    //th---根据是否启用查询节点
    @RequestMapping(value = "/querryQhseElements/{status}", method = RequestMethod.GET, produces = {"application/json;charset=utf-8"})
    public R querryQhseManagerSysElements(@PathVariable("status") int status){
        return qhseManageSysElementsService.queryAllElements(status);
    }

    //th---跟新状态
    @RequestMapping(value = "/updateQHSEElementStatus", method = RequestMethod.PUT, produces = {"application/json;charset=utf-8"})
    public String updateQHSEElementStatus(@RequestBody(required = false) QhseElementsPojo qhseManageSysElements){
        return qhseManageSysElementsService.updateElementStatus(qhseManageSysElements);
    }
    //---更新内容
    @RequestMapping(value = "/updateQHSEElement", method = RequestMethod.PUT, produces = {"application/json;charset=utf-8"})
    public String updateQHSEElement(@RequestBody(required = false) QhseElementsPojo qhseManageSysElement){
        return qhseManageSysElementsService.updateElementcontent(qhseManageSysElement);
    }
    //---添加节点内容
    @RequestMapping(value = "/addQHSEElement", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public String addQHSEElement(@RequestBody(required = false) QhseElementsPojo qhseManageSysElement){
        return qhseManageSysElementsService.addElement(qhseManageSysElement);
    }

    //新增年度qhse管理体系要素
    //前端二级节点用分号隔开，tableID
    //YearElementsDto
    @RequestMapping(value = "/addQHSEYearElement", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public R addQHSEYearElement(@RequestBody(required = false) YearElementsDto yearElementsDto){
        return qhseManageSysElementsService.addYearElement(yearElementsDto);
    }




    //---------------旧代码区----------------------------------------
    @RequestMapping(value = "/querryQHSEChildRules/{status}", method = RequestMethod.GET, produces = {"application/json;charset=utf-8"})
    public String querryQHSEChildRules(@PathVariable("status") int status){
        return qhseManageSysElementsService.querryAllRules(status);
    }

    @RequestMapping(value = "/addQHSERule", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public String addQHSERule(@RequestBody(required = false) QHSEManageSysElements qhseManageSysElements){
        return qhseManageSysElementsService.addQHSERule(qhseManageSysElements);
    }


    @RequestMapping(value = "/updateQHSERuleStatus", method = RequestMethod.PUT, produces = {"application/json;charset=utf-8"})
    public String updateQHSERuleStatus(@RequestBody(required = false) QHSEManageSysElements qhseManageSysElements){
        return qhseManageSysElementsService.updateStatus(qhseManageSysElements);
    }

    @RequestMapping(value = "/updateQHSERule", method = RequestMethod.PUT, produces = {"application/json;charset=utf-8"})
    public String updateQHSERule(@RequestBody(required = false) QHSEManageSysElements qhseManageSysElements){
        return qhseManageSysElementsService.updateQHSERule(qhseManageSysElements);
    }


}
