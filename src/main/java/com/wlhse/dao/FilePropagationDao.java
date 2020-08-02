package com.wlhse.dao;

import com.wlhse.entity.FilePropagationPOJO;
import org.springframework.stereotype.Repository;

@Repository
public interface FilePropagationDao {
    int insertNewFilePropagationPlan(FilePropagationPOJO filePropagationPOJO);

}
