package com.wlhse.dto.outDto;

import java.util.LinkedList;
import java.util.List;

public class CheckRecordTreeOutDto {
    private Integer checkRecordID;
    private String checkListCode;
    private String checkListName;
    private String content;
    private String checkType;
    private String checkCategory;
    private String companyName;
    private String companyCode;
    private String checkDate;
    private String checkContent;
    private List<CheckRecordTreeOutDto> childNode = new LinkedList<>();

    public Integer getCheckRecordID() {
        return checkRecordID;
    }

    public void setCheckRecordID(Integer checkRecordID) {
        this.checkRecordID = checkRecordID;
    }

    public String getCheckListCode() {
        return checkListCode;
    }

    public void setCheckListCode(String checkListCode) {
        this.checkListCode = checkListCode;
    }

    public String getCheckListName() {
        return checkListName;
    }

    public void setCheckListName(String checkListName) {
        this.checkListName = checkListName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getCheckCategory() {
        return checkCategory;
    }

    public void setCheckCategory(String checkCategory) {
        this.checkCategory = checkCategory;
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

    public String getCheckContent() {
        return checkContent;
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
    }

    public List<CheckRecordTreeOutDto> getChildNode() {
        return childNode;
    }

    public void setChildNode(List<CheckRecordTreeOutDto> childNode) {
        this.childNode = childNode;
    }

    @Override
    public String toString() {
        return "CheckRecordTreeOutDto{" +
                "checkRecordID=" + checkRecordID +
                ", checkListCode='" + checkListCode + '\'' +
                ", checkListName='" + checkListName + '\'' +
                ", content='" + content + '\'' +
                ", checkType='" + checkType + '\'' +
                ", checkCategory='" + checkCategory + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyCode='" + companyCode + '\'' +
                ", checkDate='" + checkDate + '\'' +
                ", checkContent='" + checkContent + '\'' +
                ", childNode=" + childNode +
                '}';
    }
}
