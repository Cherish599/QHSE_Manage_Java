package com.wlhse.entity;

import lombok.Data;

/**
 * Author:melon
 * Origin:2020/8/8
 **/
@Data
public class ElementInputFileInfo {
    private Integer id;//要素新旧文件id
    private Integer QHSE_CompanyYearManagerSysElementEvidence_ID;//要素证据id
    private String elementOriginFileName;
    private String newElementFileName;
}
