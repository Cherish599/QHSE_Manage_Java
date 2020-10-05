package com.wlhse.controller;

import com.wlhse.dao.ElementReviewDao;
import com.wlhse.dao.QHSETaskDao;
import com.wlhse.dao.QhseElementsInputDao;
import com.wlhse.dto.QualityCheckTableRecordDto;
import com.wlhse.dto.inDto.ElementReviewDto;
import com.wlhse.dto.outDto.QhseEvidenceAttatchDto;
import com.wlhse.entity.QualityInputAttachPojo;
import com.wlhse.service.ElementReviewService;
import com.wlhse.service.QualityElementReviewServer;
import com.wlhse.util.GetCurrentUserIdUtil;
import com.wlhse.util.R;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * Author:melon
 * Origin:2020/10/5
 **/


@RestController("QualityElementReviewController")
@RequestMapping("/api/v3")
public class QualityElementReviewController {
    @Resource
    QualityElementReviewServer qualityElementReviewServer;

    //质量要素录入查询
    @RequestMapping(value = "/quality_query_elementReviewer", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public R elementReviewer(@RequestParam("companyCode") String companyCode,@RequestParam("year") String year) {
        return  qualityElementReviewServer.query(companyCode,year);
    }
    //要素细节附件查看
    @RequestMapping(value = "/quality_query_Attach/{id}", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public R elementReviewer(@PathVariable("id")Integer id) {
        return  qualityElementReviewServer.queryAttach(id);
    }
    //要素录入
    @RequestMapping(value = "/quality_input_element", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public R elementReviewers(@RequestBody(required = false) QualityInputAttachPojo qualityInputAttachPojo) {
        return  qualityElementReviewServer.insertAttach(qualityInputAttachPojo);
    }
}
