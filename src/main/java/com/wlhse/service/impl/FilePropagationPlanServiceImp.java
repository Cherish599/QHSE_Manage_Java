package com.wlhse.service.impl;

import com.wlhse.dao.FilePropagationDao;
import com.wlhse.dao.FilePropagationDetailDao;
import com.wlhse.dto.outDto.FilePropagationDetailDto;
import com.wlhse.entity.FilePropagationPOJO;
import com.wlhse.service.FilePropagationPlanService;
import com.wlhse.util.R;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Description:provide services to implement method
 * Author:Coco
 * create:2020-08-03 12:06 AM
 **/
public class FilePropagationPlanServiceImp implements FilePropagationPlanService {
    @Resource
    FilePropagationDao filePropagationDao;
    @Resource
    FilePropagationDetailDao filePropagationDetailDao;

    @Override
    @Transactional
    public R releasNewFilePropagationPlan(FilePropagationPOJO filePropagationPOJO, HttpServletRequest request) {

        //data that only used in api test.
        filePropagationPOJO.setStaffId(1);
        filePropagationPOJO.setStaffName("Cocoo");
        //TODO use token in request header to get employee id
      /*  filePropagationPOJO.setStaffId();
        filePropagationPOJO.setStaffName();*/
        filePropagationDao.insertNewFilePropagationPlan(filePropagationPOJO);
        FilePropagationDetailDto filePropagationDetailDto=new FilePropagationDetailDto();
        //TODO Are these attributes mean release department or receive department?
        filePropagationDetailDto.setPushCompanyCode(filePropagationPOJO.getCompanyCode());
        filePropagationDetailDto.setPushCompanyName(filePropagationPOJO.getCompanyName());

        filePropagationDetailDto.setFilePropagationID(filePropagationPOJO.getFilePropagationID());
        //data that only used in test.
        filePropagationDetailDto.setPushStaffId(2);
        filePropagationDetailDto.setPushStaffName("采臣");
        filePropagationDetailDao.addNewDetail(filePropagationDetailDto);
        return R.ok();
    }

    @Override
    public R getFilePropagationPlanDetailByStaffId(HttpServletRequest request) {
        //TODO use token in request header to get employee id
        //data that only used in api test.
        R r=new R();
        r.put("data",filePropagationDetailDao.getFilePropagationByStaffId(2));
        return r;
    }

    @Override
    public R readFilePropagation(HttpServletRequest request, int detailId) {
        //TODO use token in request header to get employee id
        //the employee id is used to identify if this detail is belong to the employee
         //data that only used in api test.
        filePropagationDetailDao.updateFilePropagationStatus(detailId,2);
        return R.ok();
    }
}
