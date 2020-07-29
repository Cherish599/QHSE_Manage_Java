package com.wlhse.dto.inDto;




public class ElementReviewDto {
    private Integer qHSE_CompanyYearManagerSysElement_ID;
    private String code;
    private String companyName;
    private String companyCode;
    private String year;
    private String status;
    private Integer checkStaffID;
    private Integer approverStaffID;

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public Integer getqHSE_CompanyYearManagerSysElement_ID() {
        return qHSE_CompanyYearManagerSysElement_ID;
    }

    public void setqHSE_CompanyYearManagerSysElement_ID(Integer qHSE_CompanyYearManagerSysElement_ID) {
        this.qHSE_CompanyYearManagerSysElement_ID = qHSE_CompanyYearManagerSysElement_ID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCheckStaffID() {
        return checkStaffID;
    }

    public void setCheckStaffID(Integer checkStaffID) {
        this.checkStaffID = checkStaffID;
    }

    public Integer getApproverStaffID() {
        return approverStaffID;
    }

    public void setApproverStaffID(Integer approverStaffID) {
        this.approverStaffID = approverStaffID;
    }

    @Override
    public String toString() {
        return "ElementReviewDto{" +
                "qHSE_CompanyYearManagerSysElement_ID=" + qHSE_CompanyYearManagerSysElement_ID +
                ", code='" + code + '\'' +
                ", companyName='" + companyName + '\'' +
                ", year='" + year + '\'' +
                ", status='" + status + '\'' +
                ", checkStaffID='" + checkStaffID + '\'' +
                ", approverStaffID='" + approverStaffID + '\'' +
                '}';
    }
}
