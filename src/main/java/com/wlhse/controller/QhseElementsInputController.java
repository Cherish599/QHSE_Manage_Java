package com.wlhse.controller;


import com.wlhse.dto.inDto.ElementEvidenceAttachInDto;
import com.wlhse.dto.inDto.ElementEvidenceInDto;
import com.wlhse.dto.inDto.ElementReviewDto;
import com.wlhse.entity.QHSEManageSysElements;
import com.wlhse.service.QHSEManageSysElementsService;
import com.wlhse.service.QhseElementsInputService;
import com.wlhse.util.GetCurrentUserIdUtil;
import com.wlhse.util.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController("QhseElementsInputController")
@RequestMapping("/api/v3")
public class QhseElementsInputController {

    @Resource
    private QhseElementsInputService qhseElementsInputService;

    @Resource
    private GetCurrentUserIdUtil getCurrentUserIdUtil;

    //查询要素证据
    @RequestMapping(value = "/element_evidence", method = RequestMethod.GET, produces = {"application/json;charset=utf-8"})
    public R querryElementEvidence(@ModelAttribute ElementEvidenceInDto elementEvidenceInDto){
        return qhseElementsInputService.querryElementEvidence(elementEvidenceInDto);
    }

    //新增要素证据同时修改管理体系要素表状态
    @RequestMapping(value = "/element_evidence", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public R addElementEvidence(@RequestBody(required = false) ElementEvidenceInDto elementEvidenceInDto, HttpServletRequest request ){
        elementEvidenceInDto.setApproverStaffID(getCurrentUserIdUtil.getUserId(request));
        elementEvidenceInDto.setCheckStaffID(getCurrentUserIdUtil.getUserId(request));
        return qhseElementsInputService.addElementEvidence(elementEvidenceInDto);
    }

    //新增要素证据附件
    @RequestMapping(value = "/element_evidence_attach", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public R addElementEvidenceAttach(@RequestBody(required = false) ElementEvidenceAttachInDto elementEvidenceAttachInDto){
        return qhseElementsInputService.addElementEvidenceAttach(elementEvidenceAttachInDto);
    }



}
