package com.wlhse.dto;

public class FileAuditRecordDto {
    private Integer quality_FileAudit_RecordID;
    private Integer fileAuditId;
    private String code;
    private String codeScore;
    private String pass;
    private String companyCode;
    private String companyName;
    private String auditTime;
    private String additor;

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getAdditor() {
        return additor;
    }

    public void setAdditor(String additor) {
        this.additor = additor;
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

    public Integer getquality_FileAudit_RecordID() {
        return quality_FileAudit_RecordID;
    }

    public void setquality_FileAudit_RecordID(Integer qHSE_FileAudit_RecordID) {
        this.quality_FileAudit_RecordID = qHSE_FileAudit_RecordID;
    }

    public Integer getFileAuditId() {
        return fileAuditId;
    }

    public void setFileAuditId(Integer fileAuditId) {
        this.fileAuditId = fileAuditId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeScore() {
        return codeScore;
    }

    public void setCodeScore(String codeScore) {
        this.codeScore = codeScore;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
