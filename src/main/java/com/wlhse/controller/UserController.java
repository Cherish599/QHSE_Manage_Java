package com.wlhse.controller;

import com.wlhse.cache.JedisClient;
import com.wlhse.dto.inDto.UserDto;
import com.wlhse.entity.UserPojo;
import com.wlhse.service.UserService;
import com.wlhse.util.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("UserController")
@RequestMapping("/api/v3")
public class UserController {

    @Resource
    private UserService service;
    @Resource
    JedisClient jedisClient;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public String Login1(@RequestBody(required = false) UserDto userDto, HttpServletRequest request) {
        return service.login(userDto,request);
    }

    @RequestMapping(value = "/reset_pwd", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public String ResetPwd(@RequestBody(required = false) UserPojo userPojo) {
        return service.reset(userPojo);
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST,produces = "application/json; charset=utf-8" )
    public R logout(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        jedisClient.delManyCahce(token,0);
        return R.ok();
    }

    //0 返回安全体系菜单，1返回质量体系菜单
    @RequestMapping(value = "/selectModule/{type}",method = RequestMethod.GET)
    public R selectModule(@PathVariable("type")Integer type){
        Map<String, Object> resultMap=new HashMap<>();
        List<String> resultList=new ArrayList<>();
        if (type==0){
            resultList.add("0001");
            resultList.add("0002");
            resultList.add("0003");
            resultList.add("0004");
            resultList.add("0005");
            resultList.add("0006");
            resultMap.put("data",resultList);
            return R.ok(resultMap);
        }
        {
            resultList.add("0005");
            resultList.add("0006");
            resultList.add("0007");
            resultList.add("0008");
            resultMap.put("data",resultList);
            return R.ok(resultMap);
        }
    }
}
