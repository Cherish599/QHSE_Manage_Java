package com.wlhse.service;

import com.wlhse.entity.QHSEManageSysElements;


public interface QHSEManageSysElementsService {
    String querryAllRules(Integer status);
    String addQHSERule(QHSEManageSysElements rule);
    String updateStatus(QHSEManageSysElements rule);

    String updateQHSERule(QHSEManageSysElements qhseManageSysElements);
}
