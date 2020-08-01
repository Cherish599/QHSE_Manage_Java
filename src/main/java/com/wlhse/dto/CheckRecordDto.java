package com.wlhse.dto;

public class CheckRecordDto {
    private Integer checkRecordID;
    private String code;
    private String name;
    private String checkType;
    private String companyName;
    private String companyCode;
    private String checkDate;
    private String problems;
    private String checkTypeCode;
    private String pass;
    private String checkPersonId;
    private String checkPerson;

    @Override
    public String toString() {
        return "CheckRecordDto{" +
                "checkRecordID=" + checkRecordID +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", checkType='" + checkType + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyCode='" + companyCode + '\'' +
                ", checkDate='" + checkDate + '\'' +
                ", problems='" + problems + '\'' +
                ", checkTypeCode='" + checkTypeCode + '\'' +
                ", pass='" + pass + '\'' +
                ", checkPersonId='" + checkPersonId + '\'' +
                ", checkPerson='" + checkPerson + '\'' +
                '}';
    }

    public String getProblems() {
        return problems;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }

    public String getCheckTypeCode() {
        return checkTypeCode;
    }

    public void setCheckTypeCode(String checkTypeCode) {
        this.checkTypeCode = checkTypeCode;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCheckPersonId() {
        return checkPersonId;
    }

    public void setCheckPersonId(String checkPersonId) {
        this.checkPersonId = checkPersonId;
    }

    public String getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson;
    }

    public Integer getCheckRecordID() {
        return checkRecordID;
    }

    public void setCheckRecordID(Integer checkRecordID) {
        this.checkRecordID = checkRecordID;
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



    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

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

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }


}
