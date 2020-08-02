package com.wlhse.entity;

/**
 * Description:package the attributes of file propagation plan
 * Author:Coco
 * create:2020-08-02 10:41 PM
 **/
public class FilePropagationPOJO {
    private int filePropagationID;
    private String fileName;
    private String companyCode;
    private String companyName;
    private String propagationDate;
    private String description;
    private int staffId;
    private String staffName;

    public int getFilePropagationID() {
        return filePropagationID;
    }

    public void setFilePropagationID(int filePropagationID) {
        this.filePropagationID = filePropagationID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
}
