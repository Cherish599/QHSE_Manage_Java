package com.wlhse.service;

import com.wlhse.util.R;

public interface MesSumDataService {
    R getAllSumDate();

    R getMesCheckDataByDate(String date);

    R deleteSumData(int id);

    R deleteByDate(String date);
}
