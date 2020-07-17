package com.wlhse.dto.inDto;

public class QSHEMSElementInDto {
    private Integer qhseManagerSysElementID;

    private String code;

    private String name;

    private String content;

    private String basis;

    private String auditMode;

    private Integer initialScore;

    private String formula;

    private String problemDescription;

    private Integer totalCount;

    private String status;

    public Integer getQhseManagerSysElementID() {
        return qhseManagerSysElementID;
    }

    public void setQhseManagerSysElementID(Integer qhseManagerSysElementID) {
        this.qhseManagerSysElementID = qhseManagerSysElementID;
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

    @Override
    public String toString() {
        return "QSHEMSElementInDto{" +
                "qhseManagerSysElementID=" + qhseManagerSysElementID +
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
                '}';
    }
}
