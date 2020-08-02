package com.wlhse.service;


import com.wlhse.entity.FilePropagationPOJO;
import com.wlhse.util.R;

import javax.servlet.http.HttpServletRequest;

public interface FilePropagationPlanService {
    R releasNewFilePropagationPlan(FilePropagationPOJO filePropagationPOJO, HttpServletRequest request);
    R getFilePropagationPlanDetailByStaffId(HttpServletRequest request);
    R readFilePropagation(HttpServletRequest request,int detailId);
}
