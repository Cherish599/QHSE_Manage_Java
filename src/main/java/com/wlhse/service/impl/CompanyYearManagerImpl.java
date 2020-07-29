package com.wlhse.service.impl;

import com.wlhse.dao.CompanyYearManagerDao;
import com.wlhse.dao.QhseElementsInputDao;
import com.wlhse.dto.inDto.CompanyYearManagerDto;
import com.wlhse.exception.WLHSException;
import com.wlhse.service.CompanyYearManagerService;
import com.wlhse.util.R;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class CompanyYearManagerImpl implements CompanyYearManagerService {
    @Resource
    private CompanyYearManagerDao companyYearManagerDao;
    @Resource
    QhseElementsInputDao qhseElementsInputDao;
    @Override
    public R updateStatus(int id) {
        if(companyYearManagerDao.updateAll(id)<=0)
            throw new WLHSException("更新失败");
        return R.ok();
    }

    @Override
    public R queryAll(CompanyYearManagerDto companyYearManagerDto) {
       List<CompanyYearManagerDto> pojo=companyYearManagerDao.queryAll(companyYearManagerDto);
        Map<String, Object> map = new HashMap<>();
        map.put("data", pojo);
        return R.ok(map);
    }

    @Override
    public R deleteALL(int id) {
        //Should use transactions to delete data from those tables.Ensure the atomicity of database transactions.
        //TODO Use transactions.
        try {
            companyYearManagerDao.deleteAll(id);
            List<Integer> companyManagerSysElementId = qhseElementsInputDao.getCompanyManagerSysElementId(id);
            qhseElementsInputDao.deleteFromCompanyManagerSysElement(id);
            Iterator<Integer> iterator = companyManagerSysElementId.iterator();
            Iterator<Integer> iterator1=iterator;
            List<List<Integer>> companyYearManagerSysElementEvidenceId=new ArrayList<>();
            while (iterator.hasNext()){
                companyYearManagerSysElementEvidenceId.add(qhseElementsInputDao.getCompanyYearManagerSysElementEvidenceId(iterator.next()));
            }
            while (iterator1.hasNext()){
                qhseElementsInputDao.deleteFromCompanyYearManagerSysElementEvidence(iterator1.next());
            }
            while (companyYearManagerSysElementEvidenceId.iterator().hasNext()){
                while (companyYearManagerSysElementEvidenceId.iterator().next().iterator().hasNext()){
                    qhseElementsInputDao.deleteFromCompanyYearManagerSysElementEvidenceAttach(companyYearManagerSysElementEvidenceId.iterator()
                            .next().iterator().next());
                }
            }
        }
        catch (Exception e) {
            throw new WLHSException("删除失败");
        }
        return R.ok();
    }
    @Override
    public R addCompanyYearManager(CompanyYearManagerDto companyYearManagerDto) {
        if(companyYearManagerDao.addCompanyYearManager(companyYearManagerDto)<=0)
            throw new WLHSException("新增失败");
        return R.ok();
    }

}
