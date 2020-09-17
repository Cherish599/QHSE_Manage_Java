package com.wlhse.controller;

import com.wlhse.dao.MonitorFileDao;
import com.wlhse.dto.QualityCheckTableRecordAttachInfoDto;
import com.wlhse.dto.QualityCheckTableRecordDto;
import com.wlhse.dto.inDto.FilePropagationFileInfo;
import com.wlhse.entity.ElementInputFileInfo;
import com.wlhse.service.QhseElementsInputService;
import com.wlhse.service.QualityCheckTableRecordService;
import com.wlhse.service.UploadService;
import com.wlhse.util.IdUtil;
import com.wlhse.util.R;
import com.wlhse.util.state_code.CodeDict;
import com.wlhse.util.state_code.NR;
import com.wlhse.util.token.TokenUtil;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/v3")
@Controller("FileUploadUtils")
public class FileUploadUtils {

    @Resource
    private UploadService uploadService;
    @Resource
    private QhseElementsInputService qhseElementsInputService;

    @Resource
    private QualityCheckTableRecordService qualityCheckTableRecordService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    MonitorFileDao monitorFileDao;
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
    public String uploadEvidence(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) throws Exception {
        String originName="";
        originName=request.getParameter("fileName");
        logger.info("fileName:"+originName);
        if (file.isEmpty()) {
            return NR.r(CodeDict.CODE_MESSAGE, -1, CodeDict.UPLOAD_EMPTY, null, null, 0, 0);
        } else {
            ElementInputFileInfo elementInputFileInfo = new ElementInputFileInfo();
            elementInputFileInfo.setElementOriginFileName(originName);
            String fileName = setFile(file, "resources\\QHSEEvidence\\");
            elementInputFileInfo.setNewElementFileName(fileName);
            qhseElementsInputService.insertNewOriginFileName(elementInputFileInfo);
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

    @RequestMapping(value = "/QualityCheck_excel_upload", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public R uploadQuality_Check(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return R.error(CodeDict.UPLOAD_EMPTY,"上传文件为空");
        } else {
            String fileName = setFile(file, "CheckList\\");
            String path = System.getProperty("catalina.home") + "\\webapps\\CheckList\\" + fileName;
            return uploadService.uploadQualityCheck(path);
        }
    }

    @RequestMapping(value = "/uploaddanger", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String uploadDanger(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        //上传图片
        if (file.isEmpty()) {
            return NR.r(CodeDict.CODE_MESSAGE, -1, CodeDict.UPLOAD_EMPTY, null, null, 0, 0);
        } else if ("jpg".equals(file.getOriginalFilename().split("\\.")[1].toLowerCase()) || "png".equals(file.getOriginalFilename().split("\\.")[1].toLowerCase()) ||
                "bmp".equals(file.getOriginalFilename().split("\\.")[1].toLowerCase())) {
            String fileName = setFile(file, "resources\\QHSEDanger\\photoes");
            return NR.r(CodeDict.CODE_MESSAGE_DATA, 0, 0, fileName, null, 0, 0);
        } else {
            return NR.r(CodeDict.CODE_MESSAGE, -1, CodeDict.UPLOAD_TYPE_ERROR, null, null, 0, 0);
        }
    }

    @RequestMapping(value = "/uploadregulation", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String uploadregulation(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        //上传图片
        if (file.isEmpty()) {
            return NR.r(CodeDict.CODE_MESSAGE, -1, CodeDict.UPLOAD_EMPTY, null, null, 0, 0);
        } else if ("jpg".equals(file.getOriginalFilename().split("\\.")[1].toLowerCase()) || "png".equals(file.getOriginalFilename().split("\\.")[1].toLowerCase()) ||
                "bmp".equals(file.getOriginalFilename().split("\\.")[1].toLowerCase())) {
            String fileName = setFile(file, "resources\\QHSERegulation\\photoes");
            return NR.r(CodeDict.CODE_MESSAGE_DATA, 0, 0, fileName, null, 0, 0);
        } else {
            return NR.r(CodeDict.CODE_MESSAGE, -1, CodeDict.UPLOAD_TYPE_ERROR, null, null, 0, 0);
        }
    }

    //上传远程监控截图,直接将截图下载链接放置于响应结果中
    @RequestMapping(value = "/uploadScreenShot",method = RequestMethod.POST)
    @ResponseBody
    public String uploadScreenShot(@RequestParam(value = "file",required = false)MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return NR.r(CodeDict.CODE_MESSAGE, -1, CodeDict.UPLOAD_EMPTY, null, null, 0, 0);
        } else if ("jpg".equals(file.getOriginalFilename().split("\\.")[1].toLowerCase()) || "png".equals(file.getOriginalFilename().split("\\.")[1].toLowerCase()) ||
                "bmp".equals(file.getOriginalFilename().split("\\.")[1].toLowerCase())) {
            String originFileName=file.getOriginalFilename();
            String fileName = setFile(file, "RemoteMonitor\\screenShot");
            monitorFileDao.insertNewFile(fileName,originFileName);
            //拼接生成图片下载链接链接
            String downloadLink="/screenShotDownload?fileName="+fileName;
            return NR.r(CodeDict.CODE_MESSAGE_DATA, 0, 0, downloadLink, null, 0, 0);
        } else {
            return NR.r(CodeDict.CODE_MESSAGE, -1, CodeDict.UPLOAD_TYPE_ERROR, null, null, 0, 0);
        }
    }

    @RequestMapping(value = "/addQualityInformAndAttach", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public R addQualityInformAndAttach(@RequestParam(value = "file", required = false) MultipartFile file[],QualityCheckTableRecordDto qualityCheckTableRecordDto) throws Exception {
        if (file != null) {
            String attachFilePath = "";
            String picFilePath = "";
            List<QualityCheckTableRecordAttachInfoDto> Plist = new ArrayList<>();
            for (int i = 0; i < file.length; i++) {
                if (!file[i].isEmpty()) {
                    String OriginalFilename = file[i].getOriginalFilename();
                    QualityCheckTableRecordAttachInfoDto AttachInfoDto = new QualityCheckTableRecordAttachInfoDto();
                    AttachInfoDto.setAttachOriginName(OriginalFilename);
                    String FilePath = setFile(file[i], "resources\\QualityCheck");
                    AttachInfoDto.setAttachFilePath(FilePath);
                    if ("jpg".equals(OriginalFilename.split("\\.")[1].toLowerCase()) || "png".equals(OriginalFilename.split("\\.")[1].toLowerCase()) ||
                            "bmp".equals(OriginalFilename.split("\\.")[1].toLowerCase())) {
                        picFilePath += (FilePath + ";");
                    } else {
                        attachFilePath += (FilePath + ";");
                    }
                    Plist.add(AttachInfoDto);
                }
            }
            if(!attachFilePath.equals(""))
            qualityCheckTableRecordDto.setAttach(attachFilePath.substring(0, attachFilePath.length() - 1));
            if(!picFilePath.equals(""))
            qualityCheckTableRecordDto.setPic(picFilePath.substring(0, picFilePath.length() - 1));
            //System.out.println(qualityCheckTableRecordDto);
            return qualityCheckTableRecordService.addInformAndAttach(qualityCheckTableRecordDto,Plist);
        }
        else return R.error(CodeDict.UPLOAD_EMPTY, "上传文件为空");
    }
}
