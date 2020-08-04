package com.wlhse.service;


import com.wlhse.dto.inDto.FilePropagationFileInfo;

public interface UploadService {

    String uploadEmployees(String path) throws Exception;

    String uploadReports(String path) throws Exception;

    String uploadCheckList(String path) throws Exception;

    String uploadQHSEManageSysElements(String path) throws Exception;//管理要素审核excel录入数据库

    boolean insertFilePropagationFileRecord(FilePropagationFileInfo filePropagationFileInfo);
}
