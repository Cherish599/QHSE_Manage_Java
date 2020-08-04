package com.wlhse.service;


import com.wlhse.dto.outDto.FilePropagationDetailDto;
import com.wlhse.entity.FilePropagationPOJO;
import com.wlhse.util.R;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FilePropagationPlanService {
    R releaseNewFilePropagationPlan(FilePropagationPOJO filePropagationPOJO, HttpServletRequest request);
    R getFilePropagationPlanDetailByStaffId(HttpServletRequest request);
    R readFilePropagation(HttpServletRequest request,int detailId);

    R getAllFilePropagation();

    R getFilePropagationDetailIdByPropagationId(int filePropagationId);

    R insertNewFilePropagationDetail(List<FilePropagationDetailDto> filePropagationDetailDto);

    R deleteFilePropagationPlan(int id);

    R deleteFilePropagationPlanDetail(int id);
}
