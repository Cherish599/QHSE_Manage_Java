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
        R ok = R.ok();
        ok.put("data", qhseElementsInputDao.queryElementsEvidence(elementEvidenceInDto));
        return ok;
    }

    @Transactional
    @Override
    public R addElementEvidence(ElementEvidenceInDto elementEvidenceInDto) {
        EmployeeDto employeeDto = employeeManagementDao.getEmployeePojo(elementEvidenceInDto.getApproverStaffID());
        elementEvidenceInDto.setApproverStaffName(employeeDto.getName());
        elementEvidenceInDto.setCheckStaffName(employeeDto.getName());
        int i = qhseElementsInputDao.addElementsEvidence(elementEvidenceInDto);
        int j = qhseElementsInputDao.updateElementsStatus(elementEvidenceInDto.getQhseCompanyYearManagerSysElementID());
        if(i*j<=0)
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
