package com.wlhse.service;

import com.wlhse.dto.inDto.YearElementsDto;
import com.wlhse.entity.QHSEManageSysElements;
import com.wlhse.entity.QhseElementsPojo;
import com.wlhse.util.R;


public interface QHSEManageSysElementsService {
    //------旧代码区----------------
    String querryAllRules(Integer status);

    String addQHSERule(QHSEManageSysElements rule);

    String updateStatus(QHSEManageSysElements rule);

    String updateQHSERule(QHSEManageSysElements qhseManageSysElements);

    //--------新代码区--------------
    //th-查询基本数据表
    R queryAllElement();
    //th-查询基本数据表两级
    R queryChildElement();
    //th---根据是否启用查询节点
    R queryAllElements(int status);
    //th---更新状态
    String updateElementStatus(QhseElementsPojo rule);
    //th---更新内容
    String updateElementcontent(QhseElementsPojo qhseManageSysElement);
    //th---添加节点内容
    String addElement(QhseElementsPojo qhseManageSysElement);

    R addYearElement(YearElementsDto yearElementsDto);
}
