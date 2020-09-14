package com.wlhse.controller;


import com.wlhse.dao.DangerRecordDao;
import com.wlhse.dao.FileDao;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;


/**
 * Description:download file propagation file
 * Author:Coco
 * create:2020-08-04 5:04 PM
 **/
@RequestMapping("/api/v3")
@Controller("FileDownloadController")
public class FileDownloadController {
    @Resource
    FileDao fileDao;
    @RequestMapping(value = "/downloadFilePropagationFile", method = RequestMethod.GET, produces = {"application/json;charset=utf-8"})
        public ResponseEntity<byte[]> downloadFile(@RequestParam(value = "fileName")String fileName, HttpServletRequest request) throws IOException {
        String path =System.getProperty("catalina.home") + "\\webapps\\" + "FilePropagation\\";
        File file = new File(path + File.separator + fileName);
        System.out.println(file.getPath());
        fileName=fileDao.getFilePropagationOriginFileName(fileName);
        System.out.println("原文件名: "+fileName);
        HttpHeaders headers = new HttpHeaders();
        //Solve the garbled problem
        String downloadFileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
        headers.setContentDispositionFormData("attachment", downloadFileName);
        //application/octet-stream ： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/downloadDangerFile", method = RequestMethod.GET, produces = {"application/json;charset=utf-8"})
    public ResponseEntity<byte[]> downloadDangerFile(@RequestParam(value = "filename")String filename, HttpServletRequest request) throws IOException {
        String path =System.getProperty("catalina.home") +"\\webapps\\" + "\\resources\\" + "QHSEDanger\\" + "photoes\\";
        File file = new File(path + File.separator + filename);
        System.out.println(file.getPath());
        HttpHeaders headers = new HttpHeaders();
        //Solve the garbled problem
        String downloadFileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");
        headers.setContentDispositionFormData("attachment", downloadFileName);
        //application/octet-stream ： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/downloadRegulationFile", method = RequestMethod.GET, produces = {"application/json;charset=utf-8"})
    public ResponseEntity<byte[]> downloadRegulationFile(@RequestParam(value = "filename")String filename, HttpServletRequest request) throws IOException {
        String path =System.getProperty("catalina.home") +"\\webapps\\" + "\\resources\\" + "QHSERegulation\\" + "photoes\\";
        File file = new File(path + File.separator + filename);
        System.out.println(file.getPath());
        HttpHeaders headers = new HttpHeaders();
        //Solve the garbled problem
        String downloadFileName = new String(filename.getBytes("UTF-8"),"iso-8859-1");
        headers.setContentDispositionFormData("attachment", downloadFileName);
        //application/octet-stream ： 二进制流数据（最常见的文件下载）。
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }
}
