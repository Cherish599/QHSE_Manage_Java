package com.wlhse.dto.outDto;

/**
 * Description:
 * Author:Coco
 * create:2020-08-03 1:13 PM
 **/
public class FilePropagationResultDto {
    private int filePropagationPlanDetailID;
    private String fileName;
    private String propagationDate;
    private String description;
    private String staffName;
    private String status;
    private String readTime;
    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public int getFilePropagationPlanDetailID() {
        return filePropagationPlanDetailID;
    }

    public void setFilePropagationPlanDetailID(int filePropagationPlanDetailID) {
        this.filePropagationPlanDetailID = filePropagationPlanDetailID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPropagationDate() {
        return propagationDate;
    }

    public void setPropagationDate(String propagationDate) {
        this.propagationDate = propagationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
