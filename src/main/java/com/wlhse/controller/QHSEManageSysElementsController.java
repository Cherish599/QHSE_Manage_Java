package com.wlhse.controller;


import com.wlhse.entity.QHSEManageSysElements;
import com.wlhse.service.QHSEManageSysElementsService;
import com.wlhse.util.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController("QHSEManageSysElementsController")
@RequestMapping("/api/v3")
public class QHSEManageSysElementsController {

    @Resource
    private QHSEManageSysElementsService qhseManageSysElementsService;

    @RequestMapping(value = "/querryQHSEChildRules/{status}", method = RequestMethod.GET, produces = {"application/json;charset=utf-8"})
    public String querryQHSEChildRules(@PathVariable("status") int status){
        return qhseManageSysElementsService.querryAllRules(status);
    }

    //th---查询QHSE_ManagerSysElement基本表
    @RequestMapping(value = "/querryQhseElement", method = RequestMethod.GET, produces = {"application/json;charset=utf-8"})
    public R querryQhseManagerSysElement(){
        return qhseManageSysElementsService.queryAllElement();
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
