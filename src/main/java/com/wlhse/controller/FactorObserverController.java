package com.wlhse.controller;


import com.wlhse.service.FactorObserverService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("FactorObserverController")
@RequestMapping("/api/v3")
public class FactorObserverController {
    @Resource
    private FactorObserverService factorObserverService;

    @RequestMapping(value = "/factorobserver_getall", method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public String getAll()
    {
        return factorObserverService.getAll();
    }
}
