package com.wlhse.controller;

import com.wlhse.cache.JedisUtils;
import com.wlhse.dto.inDto.ElementReviewDto;
import com.wlhse.dto.getDto.EmployeeDto;
import com.wlhse.service.ElementReviewService;
import com.wlhse.util.GetCurrentUserIdUtil;
import com.wlhse.util.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController("ElementReviewController")
@RequestMapping("/api/v3")
public class ElementReviewController {
    @Resource
    private ElementReviewService elementReviewService;
    private JedisUtils jedisUtils;

    //根据当前登录人查询审核要素
    @RequestMapping(value = "/query_elementReviewer", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public R elementReviewer(@RequestBody(required = false) ElementReviewDto elementReviewDto,HttpServletRequest request ) {
        String token=request.getHeader("Authorization");
        Jedis jedis=jedisUtils.getJedis();
        String id=jedis.get(token);
        int id1=Integer.valueOf(id);
//        GetCurrentUserIdUtil getCurrentUserIdUtil=new GetCurrentUserIdUtil();
//        int userid=getCurrentUserIdUtil.getUserId(request);
        elementReviewDto.setCheckStaffID(id1);
        System.out.println(elementReviewDto);
        return  elementReviewService.query(elementReviewDto);
    }
    //审核人通过
    @RequestMapping(value = "/pass_elementReviewer", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public R elementReviewer1(@RequestBody(required = false) ElementReviewDto elementReviewDto) {
        elementReviewDto.setStatus("未批准");
        return  elementReviewService.updateStatus(elementReviewDto);
    }
    //审核人批准
    @RequestMapping(value = "/approval_elementReviewer", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public R elementReviewer2(@RequestBody(required = false) ElementReviewDto elementReviewDto) {
        System.out.println(elementReviewDto);
        elementReviewDto.setStatus("备案待查");
        return  elementReviewService.updateStatus(elementReviewDto);
    }
    //显示要素证据信息
    @RequestMapping(value = "/show_elementReviewer/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public R show(@PathVariable("id") Integer id) {
        return  elementReviewService.queryAll(id);
    }

}
