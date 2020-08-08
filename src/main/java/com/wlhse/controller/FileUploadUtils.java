package com.wlhse.controller;

import com.wlhse.dto.inDto.FilePropagationFileInfo;
import com.wlhse.exception.WLHSException;
import com.wlhse.service.UploadService;
import com.wlhse.util.*;
import com.wlhse.util.state_code.CodeDict;
import com.wlhse.util.state_code.NR;
import com.wlhse.util.token.TokenUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

@RequestMapping("/api/v3")
@Controller("FileUploadUtils")
public class FileUploadUtils {

    @Resource
    private UploadService uploadService;

    public String setFile(MultipartFile file, String str) throws Exception {

        String rootPath = System.getProperty("catalina.home") + "\\webapps\\" + str;
        // 原始名称
        String originalFileName = file.getOriginalFilename();
        // 新文件名
        String token = TokenUtil.generateString();
        String newFileName = token + originalFileName.substring(originalFileName.lastIndexOf("."));
        File newFile = new File(rootPath + File.separator + File.separator + newFileName);
        if (!newFile.getParentFile().exists()) {
            newFile.getParentFile().mkdirs();
        }
        file.transferTo(newFile);
        return newFileName;
    }

    @RequestMapping(value = "/file_upload", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String uploadFile(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return NR.r(CodeDict.CODE_MESSAGE, -1, CodeDict.UPLOAD_EMPTY, null, null, 0, 0);
        } else {
            String fileName = setFile(file, "resources\\");
            return NR.r(CodeDict.CODE_MESSAGE_DATA, 0, 0, fileName, null, 0, 0);
        }
    }

    @RequestMapping(value = "/uploadQHSEProblem", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String uploadQHSEProblem(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {//上传图片

        if (file.isEmpty()) {
            return NR.r(CodeDict.CODE_MESSAGE, -1, CodeDict.UPLOAD_EMPTY, null, null, 0, 0);
        } else if ("jpg".equals(file.getOriginalFilename().split("\\.")[1].toLowerCase()) || "png".equals(file.getOriginalFilename().split("\\.")[1].toLowerCase()) ||
                "bmp".equals(file.getOriginalFilename().split("\\.")[1].toLowerCase())) {
            String fileName = setFile(file, "resources\\QHSEProblem\\photoes");
            return NR.r(CodeDict.CODE_MESSAGE_DATA, 0, 0, fileName, null, 0, 0);
        } else {
            return NR.r(CodeDict.CODE_MESSAGE, -1, CodeDict.UPLOAD_TYPE_ERROR, null, null, 0, 0);
        }
    }

    @RequestMapping(value = "/uploadQHSEFill", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String uploadQHSEFill(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {//上传图片和视频
        if (file.isEmpty()) {
            return NR.r(CodeDict.CODE_MESSAGE, -1, CodeDict.UPLOAD_EMPTY, null, null, 0, 0);
        } else if ("jpg".equals(file.getOriginalFilename().split("\\.")[1].toLowerCase()) || "png".equals(file.getOriginalFilename().split("\\.")[1].toLowerCase()) ||
                "bmp".equals(file.getOriginalFilename().split("\\.")[1].toLowerCase())) {
            String fileName = setFile(file, "resources\\QHSEFill\\photoes");
            return NR.r(CodeDict.CODE_MESSAGE_DATA, 0, 0, fileName, null, 0, 0);
        } else if ("mp4".equals(file.getOriginalFilename().split("\\.")[1].toLowerCase()) || "avi".equals(file.getOriginalFilename().split("\\.")[1].toLowerCase()) ||
                "flash".equals(file.getOriginalFilename().split("\\.")[1].toLowerCase()) ||
                "rmvb".equals(file.getOriginalFilename().split("\\.")[1].toLowerCase()) ||
                "rm".equals(file.getOriginalFilename().split("\\.")[1].toLowerCase())) {
            String fileName = setFile(file, "resources\\QHSEFill\\videos\\");
            return NR.r(CodeDict.CODE_MESSAGE_DATA, 0, 0, fileName, null, 0, 0);
        } else {
            return NR.r(CodeDict.CODE_MESSAGE, -1, CodeDict.UPLOAD_TYPE_ERROR, null, null, 0, 0);
        }
    }

    @RequestMapping(value = "/employees_excel_upload", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String uploadEmployees(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return NR.r(CodeDict.CODE_MESSAGE, -1, CodeDict.UPLOAD_EMPTY, null, null, 0, 0);
        } else {
            String fileName = setFile(file, "Employees\\");
            String path = System.getProperty("catalina.home") + "\\webapps\\Employees\\" + fileName;
            return uploadService.uploadEmployees(path);
        }
    }

    @RequestMapping(value = "/reports_excel_upload", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String uploadReports(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return NR.r(CodeDict.CODE_MESSAGE, -1, CodeDict.UPLOAD_EMPTY, null, null, 0, 0);
        } else {
            String fileName = setFile(file, "Reports\\");
            String path = System.getProperty("catalina.home") + "\\webapps\\Reports\\" + fileName;
            return uploadService.uploadReports(path);
        }
    }

    //checklistEXCEL上传
    @RequestMapping(value = "/check_list_excel_upload", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public R uploadCheckList(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return R.error(CodeDict.UPLOAD_EMPTY,"上传文件为空");
        } else {
            String fileName = setFile(file, "CheckList\\");
            String path = System.getProperty("catalina.home") + "\\webapps\\CheckList\\" + fileName;
            return uploadService.uploadCheckList(path);
        }
    }

    //事故上传
    @RequestMapping(value = "/accident_upload", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String uploadAccident(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return NR.r(CodeDict.CODE_MESSAGE, -1, CodeDict.UPLOAD_EMPTY, null, null, 0, 0);
        } else {
            String fileName = setFile(file, "resources\\QHSEAccident\\");
            return NR.r(CodeDict.CODE_MESSAGE_DATA, 0, 0, fileName, null, 0, 0);
        }
    }

    //事件上传
    @RequestMapping(value = "/event_upload", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String uploadEvent(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return NR.r(CodeDict.CODE_MESSAGE, -1, CodeDict.UPLOAD_EMPTY, null, null, 0, 0);
        } else {
            String fileName = setFile(file, "resources\\QHSEEvent\\");
            return NR.r(CodeDict.CODE_MESSAGE_DATA, 0, 0, fileName, null, 0, 0);
        }
    }

    //要素证据上传
    @RequestMapping(value = "/evidence_upload", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String uploadEvidence(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return NR.r(CodeDict.CODE_MESSAGE, -1, CodeDict.UPLOAD_EMPTY, null, null, 0, 0);
        } else {
            String fileName = setFile(file, "resources\\QHSEEvidence\\");
            return NR.r(CodeDict.CODE_MESSAGE_DATA, 0, 0, fileName, null, 0, 0);
        }
    }

    //---------管理要素审核excel录入数据库
    @RequestMapping(value = "/managesyselements_excel_upload", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public R uploadQHSEMSElements(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return R.error(CodeDict.UPLOAD_EMPTY,"上传文件为空");
        } else {
            String fileName = setFile(file, "ManageSysElements\\");
            String path = System.getProperty("catalina.home") + "\\webapps\\ManageSysElements\\" + fileName;
            return uploadService.uploadQHSEManageSysElements(path);
        }
    }

    //upload files for file propagation.
    @RequestMapping(value = "/propagationFileUpload",method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String uploadFilesForPropagation(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        if (file.isEmpty()){
            return NR.r(CodeDict.CODE_MESSAGE, -1, CodeDict.UPLOAD_EMPTY, null, null, 0, 0);
        }
        else {
            FilePropagationFileInfo filePropagationFileInfo=new FilePropagationFileInfo();
            filePropagationFileInfo.setOriginName(file.getOriginalFilename());
            String fileName=setFile(file,"FilePropagation\\");
            filePropagationFileInfo.setFilePath(fileName);
            IdUtil idUtil=new IdUtil(2,5,3);
            filePropagationFileInfo.setId(idUtil.getId());
            boolean b = uploadService.insertFilePropagationFileRecord(filePropagationFileInfo);
            if (b)
                 return NR.r(CodeDict.CODE_MESSAGE_DATA, 0, 0, fileName, null, 0, 0);
            else
                return NR.r(CodeDict.CODE_MESSAGE, -1, CodeDict.UPLOAD_EMPTY, null, null, 0, 0);

        }
    }
}
