package com.wlhse.dto;

public class FileAuditRecordDto {
    private Integer fileAuditRecordId;
    private Integer fileAuditId;
    private String code;
    private String codeScore;
    private String pass;
    private String year;
    private String companyName;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getFileAuditRecordId() {
        return fileAuditRecordId;
    }

    public void setFileAuditRecordId(Integer fileAuditRecordId) {
        this.fileAuditRecordId = fileAuditRecordId;
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
