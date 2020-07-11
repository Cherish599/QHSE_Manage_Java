package com.wlhse.service;


import com.wlhse.util.R;

public interface CompanyService {

    R listTreeCompany(Integer id);

    R listQhseTreeCompany();

    String getCompanyOutDto(String sonName);

//    R listContractingCompany(BaseGetDto baseGetDto,String name);
}
