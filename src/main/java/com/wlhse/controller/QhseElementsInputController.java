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
import java.util.ArrayList;
import java.util.List;

@RestController("QhseElementsInputController")
@RequestMapping("/api/v3")
public class QhseElementsInputController {

    @Resource
    private QhseElementsInputService qhseElementsInputService;


    //lhl-增加附件所有信息(前端传值code、id、uploadtime不能为空）
    @RequestMapping(value = "/addAll_evidence_attach", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public R addElementEvidenceAttachs(@RequestBody(required = false) ElementEvidenceAttachInDto elementEvidenceAttachInDto){
        return qhseElementsInputService.addElementEvidenceAttach(elementEvidenceAttachInDto) ;
    }
    //lhl-查询信息
    @RequestMapping(value = "/query_evidence_attach", method = RequestMethod.GET, produces = {"application/json;charset=utf-8"})
    public R query( ElementEvidenceAttachInDto elementEvidenceAttachInDto){
        return qhseElementsInputService.queryAll(elementEvidenceAttachInDto) ;
    }

}
