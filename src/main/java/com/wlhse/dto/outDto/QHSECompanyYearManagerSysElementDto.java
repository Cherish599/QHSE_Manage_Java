package com.wlhse.dto.outDto;

import java.time.Year;

public class QHSECompanyYearManagerSysElementDto {
    private Integer  qHSE_CompanyYearManagerSysElement_ID;
    private Integer  qHSE_CompanyYearManagerSysElementTable_ID;
    private String   code;
    private String   name;
    private String  content;
    private String  basis;
    private String  auditMode;
    private Integer initialScore;
    private String  formula;
    private String  problemDescription;
    private Integer totalCount;
    private String  status;
    private String  companyCode;
    private String  companyName;
    private String  year;

    public Integer getqHSE_CompanyYearManagerSysElement_ID() {
        return qHSE_CompanyYearManagerSysElement_ID;
    }

    public void setqHSE_CompanyYearManagerSysElement_ID(Integer qHSE_CompanyYearManagerSysElement_ID) {
        this.qHSE_CompanyYearManagerSysElement_ID = qHSE_CompanyYearManagerSysElement_ID;
    }

    public Integer getqHSE_CompanyYearManagerSysElementTable_ID() {
        return qHSE_CompanyYearManagerSysElementTable_ID;
    }

    public void setqHSE_CompanyYearManagerSysElementTable_ID(Integer qHSE_CompanyYearManagerSysElementTable_ID) {
        this.qHSE_CompanyYearManagerSysElementTable_ID = qHSE_CompanyYearManagerSysElementTable_ID;
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

    public String getBasis() {
        return basis;
    }

    public void setBasis(String basis) {
        this.basis = basis;
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

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
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

    @Override
    public String toString() {
        return "QHSECompanyYearManagerSysElementDto{" +
                "qHSE_CompanyYearManagerSysElement_ID=" + qHSE_CompanyYearManagerSysElement_ID +
                ", qHSE_CompanyYearManagerSysElementTable_ID=" + qHSE_CompanyYearManagerSysElementTable_ID +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", basis='" + basis + '\'' +
                ", auditMode='" + auditMode + '\'' +
                ", initialScore=" + initialScore +
                ", formula='" + formula + '\'' +
                ", problemDescription='" + problemDescription + '\'' +
                ", totalCount=" + totalCount +
                ", status='" + status + '\'' +
                ", companyCode='" + companyCode + '\'' +
                ", companyName='" + companyName + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
