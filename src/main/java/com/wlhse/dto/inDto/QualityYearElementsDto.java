package com.wlhse.dto.inDto;

public class QualityYearElementsDto {
    private Integer qualityCompanyYearManagerSysElementID;//主键，自增id
    private String codes;//选择的所有二级节点
    private Integer qualityCompanyYearManagerSysElementTableID;//tableid
    private String code;
    private String name;
    private String content;
    private String auditMode;
    private Integer initialScore;
    private String formula;
    private Integer totalCount;
    private String status;
    private String companyCode;
    private String companyName;
    private String year;
    private String fileCheckStatus;
    private String configStatus;
    private String schedule;
    // 测试

    public Integer getQualityCompanyYearManagerSysElementID() {
        return qualityCompanyYearManagerSysElementID;
    }

    public void setQualityCompanyYearManagerSysElementID(Integer qualityCompanyYearManagerSysElementID) {
        this.qualityCompanyYearManagerSysElementID = qualityCompanyYearManagerSysElementID;
    }

    public Integer getQualityCompanyYearManagerSysElementTableID() {
        return qualityCompanyYearManagerSysElementTableID;
    }

    public void setQualityCompanyYearManagerSysElementTableID(Integer qualityCompanyYearManagerSysElementTableID) {
        this.qualityCompanyYearManagerSysElementTableID = qualityCompanyYearManagerSysElementTableID;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getConfigStatus() {
        return configStatus;
    }

    public void setConfigStatus(String configStatus) {
        this.configStatus = configStatus;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getAuditMode() {
        return auditMode;
    }

    public void setAuditMode(String auditMode) {
        this.auditMode = auditMode;
    }

    public Integer getInitialScore() {
        return initialScore;
    }

    public void setInitialScore(Integer initialScore) {
        this.initialScore = initialScore;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }


    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFileCheckStatus() {
        return fileCheckStatus;
    }

    public void setFileCheckStatus(String fileCheckStatus) {
        this.fileCheckStatus = fileCheckStatus;
    }


}
