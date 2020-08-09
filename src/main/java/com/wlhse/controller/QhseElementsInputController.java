package com.wlhse.controller;


import com.wlhse.dto.inDto.ElementEvidenceAttachInDto;
import com.wlhse.service.QhseElementsInputService;
import com.wlhse.util.R;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@RestController("QhseElementsInputController")
@RequestMapping("/api/v3")
public class QhseElementsInputController {

    @Resource
    private QhseElementsInputService qhseElementsInputService;


    //melon-增加附件所有信息(前端传值code、id、uploadtime不能为空）
    @RequestMapping(value = "/addAll_evidence_attach", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public R addElementEvidenceAttachs(@RequestBody(required = false) ElementEvidenceAttachInDto elementEvidenceAttachInDto){
       //记得将文件最新的id插入进去

        return qhseElementsInputService.addElementEvidenceAttach(elementEvidenceAttachInDto) ;
    }
    //melon-查询信息
    @RequestMapping(value = "/query_evidence_attach", method = RequestMethod.GET, produces = {"application/json;charset=utf-8"})
    public R query(ElementEvidenceAttachInDto elementEvidenceAttachInDto){
        return qhseElementsInputService.queryAll(elementEvidenceAttachInDto) ;
    }
    //melon-要素录入文件下载
    @RequestMapping(value = "/downloadElementFile", method = RequestMethod.GET, produces = {"application/json;charset=utf-8"})
    public ResponseEntity<byte[]> downloadElementFile(@RequestParam(value = "fileName")String fileName, HttpServletRequest request) throws IOException {
        String path =System.getProperty("catalina.home") + "\\webapps\\" + "\\resources\\"+"QHSEEvidence\\";
        File file = new File(path + File.separator + fileName);
        fileName=qhseElementsInputService.queryOriginFileName(fileName);
        HttpHeaders headers = new HttpHeaders();
        String downloadFileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
        headers.setContentDispositionFormData("attachment", downloadFileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }
}
