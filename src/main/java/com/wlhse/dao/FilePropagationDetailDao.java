package com.wlhse.dao;

import com.wlhse.dto.outDto.FilePropagationDetailDto;
import org.springframework.stereotype.Repository;

@Repository
public interface FilePropagationDetailDao {
    FilePropagationDetailDto getFilePropagationByStaffId(int staffId);
    int addNewDetail(FilePropagationDetailDto filePropagationDetailDto);
    int updateFilePropagationStatus(int filePropagationPlanDetailID,int staffId );
}
