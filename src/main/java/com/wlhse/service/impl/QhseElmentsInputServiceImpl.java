package com.wlhse.service.impl;

import com.wlhse.dao.EmployeeManagementDao;
import com.wlhse.dao.QHSEManageSysElementsDao;
import com.wlhse.dao.QhseElementsInputDao;
import com.wlhse.dto.getDto.EmployeeDto;
import com.wlhse.dto.inDto.ElementEvidenceAttachInDto;
import com.wlhse.dto.inDto.ElementEvidenceInDto;
import com.wlhse.dto.inDto.ElementReviewDto;
import com.wlhse.dto.outDto.QHSECompanyYearManagerSysElementEvidenceDto;
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
        R ok = R.ok();
        try{
            QHSECompanyYearManagerSysElementEvidenceDto dto = qhseElementsInputDao.queryElementsEvidence(elementEvidenceInDto);
            String name1 = dto.getCheckStaffName()+"("+qhseElementsInputDao.getEmployeeCompany(dto.getCheckStaffID())+")";
            String name2 = dto.getApproverStaffName()+"("+qhseElementsInputDao.getEmployeeCompany(dto.getApproverStaffID())+")";
            dto.setCheckStaffName(name1);
            dto.setApproverStaffName(name2);
            System.out.println(name1);
            System.out.println(name2);
            ok.put("data", dto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new WLHSException("查询失败");
        }
        return ok;
    }

    @Transactional
    @Override
    public R addElementEvidence(ElementEvidenceInDto elementEvidenceInDto) {
        try {
            EmployeeDto empCheck = employeeManagementDao.getEmployeePojo(elementEvidenceInDto.getCheckStaffID());
            EmployeeDto empApprove = employeeManagementDao.getEmployeePojo(elementEvidenceInDto.getApproverStaffID());
            System.out.println(elementEvidenceInDto);
            System.out.println(empCheck.getName());
            elementEvidenceInDto.setApproverStaffName(empApprove.getName());
            elementEvidenceInDto.setCheckStaffName(empCheck.getName());
            //新增要素证据
            int i = qhseElementsInputDao.addElementsEvidence(elementEvidenceInDto);
            System.out.println("新增成功");
            //修改管理体系要素状态为未审核
            int j = qhseElementsInputDao.updateElementsStatus(elementEvidenceInDto.getQhseCompanyYearManagerSysElementID());
            System.out.println("修改成功");
            //补充管理体系要素表的审核人和批准人
            //先在管理体系要素种根据要素id找到要素表id，再补充
            System.out.println(elementEvidenceInDto.getQhseCompanyYearManagerSysElementID());
            int id = qhseElementsInputDao.selectElementTableID(elementEvidenceInDto.getQhseCompanyYearManagerSysElementID());
            elementEvidenceInDto.setTableID(id);
            System.out.println("补充成功");
            System.out.println(id);
            int k = qhseElementsInputDao.updateElementTableByID(elementEvidenceInDto);
            if(i*j*k<=0)
                throw new WLHSException("新增失败");
        } catch (Exception e) {
            throw new WLHSException("新增失败");
        }
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
