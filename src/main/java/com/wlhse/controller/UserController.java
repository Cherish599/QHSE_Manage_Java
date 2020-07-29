package com.wlhse.controller;

import com.wlhse.dto.inDto.UserDto;
import com.wlhse.entity.UserPojo;
import com.wlhse.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController("UserController")
@RequestMapping("/api/v3")
public class UserController {

    @Resource
    private UserService service;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public String Login1(@RequestBody(required = false) UserDto userDto, HttpServletRequest request) {
        System.out.println("登陆测试");
        return service.login(userDto,request);
    }

    @RequestMapping(value = "/reset_pwd", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public String ResetPwd(@RequestBody(required = false) UserPojo userPojo) {
        return service.reset(userPojo);
    }

}
