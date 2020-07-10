package com.wlhse.service;


import com.wlhse.dto.inDto.CheckListAddDto;

import com.wlhse.util.R;

public interface CheckListService {

    R getTreeDto();

    R addCheckListNode(CheckListAddDto checkListAddDto);

    R deleteCheckList(int id);

    R updateCheckList(int id,CheckListAddDto checkListAddDto);

}
