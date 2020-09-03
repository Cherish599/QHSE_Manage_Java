package com.wlhse.service.impl;

import com.wlhse.cache.JedisClient;
import com.wlhse.dao.CompanyYearManagerDao;
import com.wlhse.dao.QHSETaskDao;
import com.wlhse.dao.QhseElementsInputDao;
import com.wlhse.dto.CompanyYearManagerDtoWithEmployeeId;
import com.wlhse.dto.inDto.CompanyYearManagerDto;
import com.wlhse.exception.WLHSException;
import com.wlhse.service.CompanyYearManagerService;
import com.wlhse.util.R;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class CompanyYearManagerImpl implements CompanyYearManagerService {
    @Resource
    private CompanyYearManagerDao companyYearManagerDao;
    @Resource
    QhseElementsInputDao qhseElementsInputDao;
    @Resource
    JedisClient jedisClient;
    @Resource
    QHSETaskDao taskDao;
    @Override
    public R updateStatus(int id) {
        if(companyYearManagerDao.updateAll(id)<=0)
            throw new WLHSException("更新失败");
        return R.ok();
    }

    @Override
    public R queryAll(CompanyYearManagerDtoWithEmployeeId companyYearManagerDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Map<String, String> map1 = jedisClient.hGetAll(token);
        int employeeId = Integer.valueOf(map1.get("employeeId"));
        companyYearManagerDto.setEmployeeId(employeeId);
        List<CompanyYearManagerDto> pojo=companyYearManagerDao.queryAll(companyYearManagerDto);
        Map<String, Object> map = new HashMap<>();
        map.put("data", pojo);
        return R.ok(map);
    }

    @Override
    @Transactional
    public R deleteALL(int id) {
        //Should use transactions to delete data from those tables.Ensure the atomicity of database transactions.
        //TODO Use transactions.
        try {
            companyYearManagerDao.deleteAll(id);
            taskDao.deleteTask(id);
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
