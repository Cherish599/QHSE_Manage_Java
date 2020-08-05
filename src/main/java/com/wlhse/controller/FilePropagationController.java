package com.wlhse.controller;

import com.wlhse.dto.outDto.FilePropagationDetailDto;
import com.wlhse.entity.FilePropagationPOJO;
import com.wlhse.entity.FilePropagationPOJO1;
import com.wlhse.service.FilePropagationPlanService;
import com.wlhse.service.impl.FilePropagationPlanServiceImp;
import com.wlhse.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Description:
 * Author:Coco
 * create:2020-08-03 1:00 PM
 **/
@RestController("FilePropagationController")
@RequestMapping("/api/v3")
public class FilePropagationController {
    @Autowired
    FilePropagationPlanService filePropagationPlanService;

    @RequestMapping(value = "/getFilePropagationDetailList",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    R getFilePropagationList(HttpServletRequest request){
        return  filePropagationPlanService.getFilePropagationPlanDetailByStaffId(request);
    }

    @RequestMapping(value = "/readPropagation",method =RequestMethod.POST,produces = "application/json; charset=utf-8" )
    R readPropagation(HttpServletRequest request,@RequestParam(value = "detailId") int detailId){
        return  filePropagationPlanService.readFilePropagation(request,detailId);
    }

    //query all propagation plans
    @RequestMapping(value = "/queryPropagationPlan",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    R queryAll(){
        return filePropagationPlanService.getAllFilePropagation();
    }

    @RequestMapping(value = "/queryPropagationDetailAll",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    R queryAllPropagationDetailId(@RequestParam(value = "filePropagationId")int filePropagationId){
        return filePropagationPlanService.getFilePropagationDetailIdByPropagationId(filePropagationId);
    }

    @RequestMapping(value = "/insertPropagationPlan",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    R insertPropagationPlan(@RequestBody FilePropagationPOJO1 filePropagationPOJO, HttpServletRequest request){
        return filePropagationPlanService.releaseNewFilePropagationPlan(filePropagationPOJO,request);
    }

    @RequestMapping(value = "/insertPropagationDetail ",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    R insertNewPropagationDetail(@RequestBody List<FilePropagationDetailDto> filePropagationDetailDtos){
        return filePropagationPlanService.insertNewFilePropagationDetail(filePropagationDetailDtos);
    }

    @RequestMapping(value = "/deletePropagationPlan ",method = RequestMethod.DELETE,produces = "application/json; charset=utf-8")
    R deletePropagationPlan(@RequestParam(value = "filePropagationId")int id){
        return filePropagationPlanService.deleteFilePropagationPlan(id);
    }

    @RequestMapping(value ="deletePropagationDetail",method =RequestMethod.DELETE,produces = "application/json; charset=utf-8" )
    R deletePropagationDetail(@RequestParam(value = "filePropagationDetailId")int filePropagationDetailId){
        return filePropagationPlanService.deleteFilePropagationPlan(filePropagationDetailId);
    }

    @RequestMapping(value = "/getFilePropagationDetailList/{pageNum}",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    R getFilePropagationListInPage(@PathVariable(value = "pageNum")int pageNum,HttpServletRequest request){
        return filePropagationPlanService.getFilePropagationPlanDetailByStaffIdInPage(request,pageNum);
    }
}
