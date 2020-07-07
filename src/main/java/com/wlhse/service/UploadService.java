package com.wlhse.service;



public interface UploadService {

    String uploadEmployees(String path) throws Exception;

    String uploadReports(String path) throws Exception;

    String uploadCheckList(String path) throws Exception;
}
