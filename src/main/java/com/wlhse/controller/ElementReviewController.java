package com.wlhse.controller;

import com.wlhse.dto.inDto.ElementReviewDto;
import com.wlhse.dto.outDto.QhseEvidenceAttatchDto;
import com.wlhse.service.ElementReviewService;
import com.wlhse.util.GetCurrentUserIdUtil;
import com.wlhse.util.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

@RestController("ElementReviewController")
@RequestMapping("/api/v3")
public class ElementReviewController {
    @Resource
    private ElementReviewService elementReviewService;

    @Resource
    private GetCurrentUserIdUtil getCurrentUserIdUtil;

    //查询审核要素
    @RequestMapping(value = "/query_elementReviewer", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public R elementReviewer(@ModelAttribute ElementReviewDto elementReviewDto) {
        return  elementReviewService.query(elementReviewDto);
    }
    //查询批准要素
    @RequestMapping(value = "/query_elementReviewers", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public R elementReviewers(@ModelAttribute ElementReviewDto elementReviewDto,HttpServletRequest request ) {
        return  elementReviewService.queryS(elementReviewDto);
    }
    //审核人通过
    @RequestMapping(value = "/pass_elementReviewer", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public R elementReviewer1(@RequestBody(required = false) ElementReviewDto elementReviewDto,HttpServletRequest request ) {
        elementReviewDto.setCheckStaffID(getCurrentUserIdUtil.getUserId(request));
        elementReviewDto.setStatus("未批准");
        elementReviewService.updateCheck(elementReviewDto);
        return  elementReviewService.updateStatus(elementReviewDto);
    }
    //审核人批准
    @RequestMapping(value = "/approval_elementReviewer", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public R elementReviewer2(@RequestBody(required = false) ElementReviewDto elementReviewDto,HttpServletRequest request ) {
        elementReviewDto.setApproverStaffID(getCurrentUserIdUtil.getUserId(request));
        elementReviewService.updateApprove(elementReviewDto);
        elementReviewDto.setStatus("备案待查");
        return  elementReviewService.updateStatus(elementReviewDto);
    }
    //显示要素证据信息
    @RequestMapping(value = "/show_elementReviewer", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public R show(@ModelAttribute QhseEvidenceAttatchDto qhseEvidenceAttatchDto) throws ParseException {
        return  elementReviewService.queryAll(qhseEvidenceAttatchDto);
    }
    //审核人不批准不通过
    @RequestMapping(value = "/no_elementReviewer", method = RequestMethod.PUT, produces = "application/json; charset=utf-8")
    public R elementReviewer3(@RequestBody(required = false) ElementReviewDto elementReviewDto) {
        //elementReviewService.deletes(elementReviewDto);不通过删除附件等所有信息
        elementReviewDto.setStatus("不通过");
        return  elementReviewService.updateStatus(elementReviewDto);
    }
    //查询已审核或已审批
    @RequestMapping(value = "/show_approve_check", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public R elementReviewer4( ElementReviewDto elementReviewDto) {
        return  elementReviewService.shows(elementReviewDto);
    }
}
