package com.wlhse.service.impl;

import com.wlhse.dao.FilePropagationDao;
import com.wlhse.dao.FilePropagationDetailDao;
import com.wlhse.dto.outDto.FilePropagationDetailDto;
import com.wlhse.entity.FilePropagationPOJO;
import com.wlhse.service.FilePropagationPlanService;
import com.wlhse.util.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Description:provide services to implement method
 * Author:Coco
 * create:2020-08-03 12:06 AM
 **/
@Service
public class FilePropagationPlanServiceImp implements FilePropagationPlanService {
    @Resource
    FilePropagationDao filePropagationDao;
    @Resource
    FilePropagationDetailDao filePropagationDetailDao;

    @Override
    @Transactional
    public R releaseNewFilePropagationPlan(FilePropagationPOJO filePropagationPOJO, HttpServletRequest request) {

        //data that only used in api test.
        filePropagationPOJO.setStaffId(1);
        filePropagationPOJO.setStaffName("Cocoo");
        //TODO use token in request header to get employee id
      /*  filePropagationPOJO.setStaffId();
        filePropagationPOJO.setStaffName();*/
        filePropagationDao.insertNewFilePropagationPlan(filePropagationPOJO);
       /* FilePropagationDetailDto filePropagationDetailDto=new FilePropagationDetailDto();*/
        //TODO Are these attributes mean release department or receive department?
/*        filePropagationDetailDto.setPushCompanyCode(filePropagationPOJO.getCompanyCode());
        filePropagationDetailDto.setPushCompanyName(filePropagationPOJO.getCompanyName());

        filePropagationDetailDto.setFilePropagationID(filePropagationPOJO.getFilePropagationID());*/
        //data that only used in test.
   /*     filePropagationDetailDto.setPushStaffId(2);
        filePropagationDetailDto.setPushStaffName("采臣");*/
       /* filePropagationDetailDao.addNewDetail(filePropagationDetailDto);*/
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

    @Override
    public R getAllFilePropagation() {
        R r=new R();
        r.put("data",filePropagationDao.getAllFilePropagation());
        return r;
    }

    @Override
    public R getFilePropagationDetailIdByPropagationId(int filePropagationId) {
        R r=new R();
        r.put("data",filePropagationDetailDao.queryAllPropagationDetailIdByFilePropagationId(filePropagationId));
        return r;
    }

    @Override
    @Transactional
    public R insertNewFilePropagationDetail(List<FilePropagationDetailDto> filePropagationDetailDto) {
        for (FilePropagationDetailDto filePropagationDetailDto1:filePropagationDetailDto){
            //data that only used in test.
            filePropagationDetailDto1.setPushStaffName("采臣");
            filePropagationDetailDto1.setPushStaffId(2);
            filePropagationDetailDao.addNewDetail(filePropagationDetailDto1);
        }
        return R.ok();
    }

    @Override
    public R deleteFilePropagationPlan(int id) {
        filePropagationDao.deletePropagationPlan(id);
        return R.ok();
    }

    @Override
    public R deleteFilePropagationPlanDetail(int id) {
        filePropagationDetailDao.deleteFilePropagationPlanDetail(id);
        return R.ok();
    }


}
