package com.wlhse.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlhse.dao.FileDao;
import com.wlhse.dao.FilePropagationDao;
import com.wlhse.dao.FilePropagationDetailDao;
import com.wlhse.dto.inDto.FileInDto;
import com.wlhse.dto.inDto.FilePropagationFileInfo;
import com.wlhse.dto.outDto.FilePropagationDetailDto;
import com.wlhse.dto.outDto.FilePropagationResultDto;
import com.wlhse.entity.FilePropagationPOJO;
import com.wlhse.entity.FilePropagationPOJO1;
import com.wlhse.service.FilePropagationPlanService;
import com.wlhse.util.IdUtil;
import com.wlhse.util.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.security.x509.RFC822Name;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    @Resource
    FileDao fileDao;
    @Override
    @Transactional
    public R releaseNewFilePropagationPlan(FilePropagationPOJO1 filePropagationPOJO, HttpServletRequest request) {

        //data that only used in api test.
        //TODO use token in request header to get employee information
        filePropagationPOJO.setStaffId(1);
        filePropagationPOJO.setStaffName("Cocoo");
        IdUtil idUtil=new IdUtil(6,2,1);
        filePropagationPOJO.setFilePropagationID(idUtil.getId());
        FilePropagationPOJO filePropagationPOJO1=new FilePropagationPOJO();
        filePropagationPOJO1.setFilePropagationID(filePropagationPOJO.getFilePropagationID());
        filePropagationPOJO1.setStaffName(filePropagationPOJO.getStaffName());
        filePropagationPOJO1.setStaffId(filePropagationPOJO.getStaffId());
        filePropagationPOJO1.setCompanyCode(filePropagationPOJO.getCompanyCode());
        filePropagationPOJO1.setCompanyName(filePropagationPOJO.getCompanyName());
        filePropagationPOJO1.setDescription(filePropagationPOJO.getDescription());
        filePropagationPOJO1.setPropagationDate(filePropagationPOJO.getPropagationDate());
        filePropagationPOJO1.setFileName(filePropagationPOJO.getFileName());
        filePropagationDao.insertNewFilePropagationPlan(filePropagationPOJO1);
        List<String> filePaths = filePropagationPOJO.getFilePath();
        for (String filePath:filePaths){
            fileDao.updateFilePropagationID(filePath,filePropagationPOJO1.getFilePropagationID());
        }
        return R.ok();
    }

    @Override
    public R getFilePropagationPlanDetailByStaffId(HttpServletRequest request) {
        //TODO use token in request header to get employee information
        //data that only used in api test.
        R r=new R();
        List<FilePropagationResultDto> filePropagationByStaffId = filePropagationDetailDao.getFilePropagationByStaffId(2);
        for (FilePropagationResultDto filePropagationResultDto:filePropagationByStaffId){
            List<String> filePaths=new ArrayList<>();
            List<FilePropagationFileInfo> fileInfos= fileDao.getFileInfoByPropagationId(filePropagationResultDto.getFilePropagationId());
            for (FilePropagationFileInfo file:fileInfos){
                filePaths.add(file.getFilePath());
            }
            filePropagationResultDto.setFilePath(filePaths);
        }
        r.put("data",filePropagationByStaffId);
        return r;
    }

    @Override
    public R readFilePropagation(HttpServletRequest request, int detailId) {
        //TODO use token in request header to get employee information
        //the employee id is used to identify if this detail is belong to the employee
         //data that only used in api test.
        filePropagationDetailDao.updateFilePropagationStatus(detailId,2);
        return R.ok();
    }

    @Override
    public R getAllFilePropagation() {
        R r=new R();
        List<FilePropagationPOJO> allFilePropagation = filePropagationDao.getAllFilePropagation();
        System.out.println("查询结果:"+allFilePropagation);
        List<FilePropagationPOJO1> filePropagationPOJO1s=new ArrayList<>();
        for (FilePropagationPOJO filePropagation:allFilePropagation){
            List<String> filePaths=new ArrayList<>();
            FilePropagationPOJO1 filePropagationPOJO1= new FilePropagationPOJO1();
            filePropagationPOJO1.setFilePropagationID(filePropagation.getFilePropagationID());
            filePropagationPOJO1.setStaffName(filePropagation.getStaffName());
            filePropagationPOJO1.setStaffId(filePropagation.getStaffId());
            filePropagationPOJO1.setCompanyCode(filePropagation.getCompanyCode());
            filePropagationPOJO1.setCompanyName(filePropagation.getCompanyName());
            filePropagationPOJO1.setDescription(filePropagation.getDescription());
            filePropagationPOJO1.setPropagationDate(filePropagation.getPropagationDate());
            filePropagationPOJO1.setFileName(filePropagation.getFileName());
            List<FilePropagationFileInfo> fileInfo= fileDao.getFileInfoByPropagationId(filePropagation.getFilePropagationID());
            System.out.println(fileInfo.toString());
            for (FilePropagationFileInfo file1:fileInfo){
                filePaths.add(file1.getFilePath());
            }
            filePropagationPOJO1.setFilePath(filePaths);
            filePropagationPOJO1s.add(filePropagationPOJO1);
        }
        r.put("data",filePropagationPOJO1s);
        return r;
    }

    @Override
    public R getFilePropagationDetailIdByPropagationId(Long filePropagationId) {
        R r=new R();
        r.put("data",filePropagationDetailDao.queryAllPropagationDetailIdByFilePropagationId(filePropagationId));
        return r;
    }

    @Override
    @Transactional
    public R insertNewFilePropagationDetail(List<FilePropagationDetailDto> filePropagationDetailDto) {
        for (FilePropagationDetailDto filePropagationDetailDto1:filePropagationDetailDto){
            filePropagationDetailDto1.setPushStaffId(2);
            filePropagationDetailDto1.setPushStaffName("采臣");
            filePropagationDetailDao.addNewDetail(filePropagationDetailDto1);
        }
        return R.ok();
    }

    @Override
    public R deleteFilePropagationPlan(Long id) {
        filePropagationDao.deletePropagationPlan(id);
        return R.ok();
    }

    @Override
    public R deleteFilePropagationPlanDetail(int id) {
        filePropagationDetailDao.deleteFilePropagationPlanDetail(id);
        return R.ok();
    }

    @Override
    public R getFilePropagationPlanDetailByStaffIdInPage(HttpServletRequest request, int pageNum) {
        R r=new R();

        //data that only used in test.
        PageHelper.startPage(pageNum,6);
        List<FilePropagationResultDto> filePropagationByStaffId = filePropagationDetailDao.getFilePropagationByStaffId(2);
        for (FilePropagationResultDto filePropagationResultDto:filePropagationByStaffId){
            List<String> filePaths=new ArrayList<>();
            List<FilePropagationFileInfo> fileInfos= fileDao.getFileInfoByPropagationId(filePropagationResultDto.getFilePropagationId());
            for (FilePropagationFileInfo file:fileInfos){
                filePaths.add(file.getFilePath());
            }
            filePropagationResultDto.setFilePath(filePaths);
        }
        PageInfo<FilePropagationResultDto> filePropagationResultDtoPageInfo = new PageInfo<>(filePropagationByStaffId);
        r.put("data",filePropagationResultDtoPageInfo);
        return r;
    }

    @Override
    public R getReadHistoryByPropagationId(Long propagationId) {
        R r=new R();
        r.put("data",filePropagationDetailDao.getReadHistory(propagationId));
        return r;
    }


}
