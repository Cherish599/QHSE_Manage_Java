package com.wlhse.service.impl;

import com.wlhse.dao.CompanyDao;
import com.wlhse.dto.outDto.CompanyOutDto;
import com.wlhse.service.CompanyService;
import com.wlhse.util.R;
import com.wlhse.util.TreeUtil;
import com.wlhse.util.state_code.CodeDict;
import com.wlhse.util.state_code.NR;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Resource
    private CompanyDao companyDao;

    @Resource
    private TreeUtil treeUtil;

    @Override
    public R listTreeCompany(Integer id) {
        R ok = R.ok();
        ok.put("data", treeUtil.getCompanyTree(companyDao.queryCompany(id)));
        return ok;
    }

    @Override
    public String getCompanyOutDto(String sonName) {
        int max = companyDao.queryMaxLenth();
        List<CompanyOutDto> listCompanyOutSonDto = new ArrayList<>();
        List<CompanyOutDto> listCompanyOutSonDto1 = companyDao.getListCompanyOutSonDto(max, null);
        for (CompanyOutDto companyOutDto : listCompanyOutSonDto1) {
            String parentCode = companyOutDto.getSonCode().substring(0, 4);
            String parentName = companyDao.getCompanyOutDto(parentCode);
            companyOutDto.setParentCode(parentCode);
            companyOutDto.setParentName(parentName);
            listCompanyOutSonDto.add(companyOutDto);
        }
        return NR.r(listCompanyOutSonDto);
    }
}

