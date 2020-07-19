package com.wlhse.service.impl;

import com.wlhse.dao.EmployeeManagementDao;
import com.wlhse.dao.QHSEManageSysElementsDao;
import com.wlhse.dao.QhseElementsInputDao;
import com.wlhse.dto.getDto.EmployeeDto;
import com.wlhse.dto.inDto.ElementEvidenceAttachInDto;
import com.wlhse.dto.inDto.ElementEvidenceInDto;
import com.wlhse.dto.inDto.ElementReviewDto;
import com.wlhse.entity.QHSECompanySysElementsPojo;
import com.wlhse.entity.QHSEManageSysElements;
import com.wlhse.exception.WLHSException;
import com.wlhse.service.QHSEManageSysElementsService;
import com.wlhse.service.QhseElementsInputService;
import com.wlhse.util.R;
import com.wlhse.util.TreeUtil;
import com.wlhse.util.state_code.NR;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class QhseElmentsInputServiceImpl implements QhseElementsInputService {
    @Resource
    QhseElementsInputDao qhseElementsInputDao;

    @Resource
    EmployeeManagementDao employeeManagementDao;

    @Override
    public R querryElementEvidence(ElementEvidenceInDto elementEvidenceInDto) {
        System.out.println(elementEvidenceInDto.getCode());
        R ok = R.ok();
        ok.put("data", qhseElementsInputDao.queryElementsEvidence(elementEvidenceInDto));
        return ok;
    }

    @Transactional
    @Override
    public R addElementEvidence(ElementEvidenceInDto elementEvidenceInDto) {
        EmployeeDto empCheck = employeeManagementDao.getEmployeePojo(elementEvidenceInDto.getCheckStaffID());
        EmployeeDto empApprove = employeeManagementDao.getEmployeePojo(elementEvidenceInDto.getApproverStaffID());
        System.out.println(elementEvidenceInDto);
        System.out.println(empCheck.getName());
        elementEvidenceInDto.setApproverStaffName(empApprove.getName());
        elementEvidenceInDto.setCheckStaffName(empCheck.getName());
        //新增要素证据
        int i = qhseElementsInputDao.addElementsEvidence(elementEvidenceInDto);
        //修改管理体系要素状态为未审核
        int j = qhseElementsInputDao.updateElementsStatus(elementEvidenceInDto.getQhseCompanyYearManagerSysElementID());
        //补充管理体系要素表的审核人和批准人
        //先在管理体系要素种根据要素id找到要素表id，再补充
        int id = qhseElementsInputDao.selectElementTableID(elementEvidenceInDto.getQhseCompanyYearManagerSysElementID());
        elementEvidenceInDto.setTableID(id);
        int k = qhseElementsInputDao.updateElementTableByID(elementEvidenceInDto);
        if(i*j*k*id<=0)
            throw new WLHSException("新增失败");
        return R.ok();
    }

    @Override
    public R addElementEvidenceAttach(ElementEvidenceAttachInDto elementEvidenceAttachInDto) {
        //查询是否已有附件记录id
//        ElementEvidenceAttachInDto dto = qhseElementsInputDao.queryEvidenceAttach(elementEvidenceAttachInDto.getQhseCompanyYearManagerSysElementEvidenceID());
//        int i=0;
//        if(dto!=null) {//已有，修改后，更新
//            String attaches = dto.getAttach();
//            dto.setAttach(attaches+";"+elementEvidenceAttachInDto.getAttach());
//            i = qhseElementsInputDao.updateElementsEvidenceAttach(elementEvidenceAttachInDto);
//        } else {
//            i = qhseElementsInputDao.addElementsEvidenceAttach(elementEvidenceAttachInDto);
//        }
        if(qhseElementsInputDao.addElementsEvidenceAttach(elementEvidenceAttachInDto)<=0)
            throw new WLHSException("新增失败");
        return R.ok();
    }

}
