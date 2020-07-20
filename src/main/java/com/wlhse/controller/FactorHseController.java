package com.wlhse.controller;


import com.wlhse.service.FactorHseService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController("FactorHseController")
@RequestMapping("/api/v3")
public class FactorHseController {

    @Resource
    private FactorHseService factorHseService;


    @RequestMapping(value = "/factorhse_getall", method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public String getAll()
    {
        return factorHseService.getAll();
    }

}
