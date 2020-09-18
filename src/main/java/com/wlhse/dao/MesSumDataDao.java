package com.wlhse.dao;

import com.wlhse.entity.MesSumData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesSumDataDao {

    int batchInsertNewSumData(List<MesSumData> mesSumDataList);

    List<String> getAllSumDate();

    List<MesSumData> getSumDataByDate(String date);

    int deleteSumData(int id);

    int deleteByDate(String date);

    int updateMesData(MesSumData mesSumData);

    MesSumData getMesDataById(int id);
}
