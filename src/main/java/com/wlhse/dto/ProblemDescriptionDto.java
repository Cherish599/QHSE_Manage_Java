package com.wlhse.dto;

public class ProblemDescriptionDto {
    private Integer qHSE_AuditProblemRecord_ID;
    private Integer qHSE_FileAudit_ID;
    private Integer qHSE_FileAuditRecord_ID;
    private String problemDescription;
    private String code;
    private String startDate;
    private String endDate;
    private String additor;
    private String companyCode;
    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getAdditor() {
        return additor;
    }

    public void setAdditor(String additor) {
        this.additor = additor;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getqHSE_AuditProblemRecord_ID() {
        return qHSE_AuditProblemRecord_ID;
    }

    public void setqHSE_AuditProblemRecord_ID(Integer qHSE_AuditProblemRecord_ID) {
        this.qHSE_AuditProblemRecord_ID = qHSE_AuditProblemRecord_ID;
    }

    public Integer getqHSE_FileAudit_ID() {
        return qHSE_FileAudit_ID;
    }

    public void setqHSE_FileAudit_ID(Integer qHSE_FileAudit_ID) {
        this.qHSE_FileAudit_ID = qHSE_FileAudit_ID;
    }

    public Integer getqHSE_FileAuditRecord_ID() {
        return qHSE_FileAuditRecord_ID;
    }

    public void setqHSE_FileAuditRecord_ID(Integer qHSE_FileAuditRecord_ID) {
        this.qHSE_FileAuditRecord_ID = qHSE_FileAuditRecord_ID;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
