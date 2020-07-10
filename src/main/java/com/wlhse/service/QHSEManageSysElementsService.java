package com.wlhse.service;

import com.wlhse.entity.QHSEManageSysElements;
import com.wlhse.util.R;


public interface QHSEManageSysElementsService {
    String querryAllRules(Integer status);

    //th-查询基本数据表
    R queryAllElement();

    String addQHSERule(QHSEManageSysElements rule);

    String updateStatus(QHSEManageSysElements rule);

    String updateQHSERule(QHSEManageSysElements qhseManageSysElements);
}
