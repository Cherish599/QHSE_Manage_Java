package com.wlhse.util;

import com.wlhse.dto.inDto.QSHEMSElementInDto;
import java.util.List;

public class PoiMSElement {

    public static String isDuplicelements(List<QSHEMSElementInDto> elements) {
        String duplic = null;
        for (int i = 0; i < elements.size(); i++) {
            for (int j = i + 1; j < elements.size(); j++) {
                if (elements.get(i).getCode().equals(elements.get(j).getCode())) {
                    duplic = elements.get(i).getCode();
                    break;
                }
            }
        }
        return duplic;
    }

}