package com.wlhse.service;

import com.wlhse.entity.QHSEManageSysElements;
import com.wlhse.entity.QhseElementsPojo;
import com.wlhse.util.R;


public interface QHSEManageSysElementsService {
    String querryAllRules(Integer status);

    //th-查询基本数据表
    R queryAllElement();

    //th-查询基本数据表两级
    R queryChildElement();
    //th---跟新状态
    String updateElementStatus(QhseElementsPojo rule);


    String addQHSERule(QHSEManageSysElements rule);

    String updateStatus(QHSEManageSysElements rule);

    String updateQHSERule(QHSEManageSysElements qhseManageSysElements);
}
