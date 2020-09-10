package com.wlhse.dao;

import com.wlhse.entity.MesSumData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesSumDataDao {

    int batchInsertNewSumData(List<MesSumData> mesSumDataList);
}
