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

    @Resource
    private GetCurrentUserIdUtil getCurrentUserIdUtil;

    //根据当前登录人查询审核要素
    @RequestMapping(value = "/query_elementReviewer", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public R elementReviewer(@RequestBody(required = false) ElementReviewDto elementReviewDto,HttpServletRequest request ) {
        elementReviewDto.setCheckStaffID(getCurrentUserIdUtil.getUserId(request));
        return  elementReviewService.query(elementReviewDto);
    }
    //根据当前登录人查询批准要素
    @RequestMapping(value = "/query_elementReviewers", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public R elementReviewers(@RequestBody(required = false) ElementReviewDto elementReviewDto,HttpServletRequest request ) {
        elementReviewDto.setApproverStaffID(getCurrentUserIdUtil.getUserId(request));
        return  elementReviewService.queryS(elementReviewDto);
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
