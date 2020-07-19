package com.wlhse.controller;

import com.wlhse.dto.inDto.ElementReviewDto;
import com.wlhse.dto.inDto.QHSEManageSysElementsDto;
import com.wlhse.entity.QHSECompanyYearManagerSysElementPojo;
import com.wlhse.entity.QHSEManageSysElements;
import com.wlhse.service.QhseChecklistManageSysService;
import com.wlhse.util.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v3")

public class QhseChecklistManageSysController {
    @Resource
    private QhseChecklistManageSysService qhseChecklistManageSysService;
    //lhl-添加年度要素表、要素细节表
    @RequestMapping(value = "/add_elementReviewer", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public R elementReviewer(@RequestBody QHSEManageSysElementsDto qHSEManageSysElementsDto) {
        List<QHSECompanyYearManagerSysElementPojo> list=qhseChecklistManageSysService.query(qHSEManageSysElementsDto);
        QHSEManageSysElementsDto Dto=qhseChecklistManageSysService.query2(qHSEManageSysElementsDto);
        if(Dto==null) {
            qhseChecklistManageSysService.add(qHSEManageSysElementsDto);
            QHSEManageSysElementsDto qHSEManageSysElementsDtos = qhseChecklistManageSysService.query2(qHSEManageSysElementsDto);
            return qhseChecklistManageSysService.addAll(list, qHSEManageSysElementsDtos);
        }
        return R.error(200,"年度表已经存在");
    }

}
