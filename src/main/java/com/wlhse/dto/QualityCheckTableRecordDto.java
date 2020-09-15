package com.wlhse.dto;

public class QualityCheckTableRecordDto {
    Integer qualityCheckTableRecordID;
    Integer qualityCheckID;
    String qualityCheckName;
    String checkListCode;
    String checkStatus;
    String description;
    String checkResult;
    String attach;
    String pic;

    public Integer getQualityCheckTableRecordID() {
        return qualityCheckTableRecordID;
    }

    public void setQualityCheckTableRecordID(Integer qualityCheckTableRecordID) {
        this.qualityCheckTableRecordID = qualityCheckTableRecordID;
    }

    public Integer getQualityCheckID() {
        return qualityCheckID;
    }

    public void setQualityCheckID(Integer qualityCheckID) {
        this.qualityCheckID = qualityCheckID;
    }

    public String getQualityCheckName() {
        return qualityCheckName;
    }

    public void setQualityCheckName(String qualityCheckName) {
        this.qualityCheckName = qualityCheckName;
    }

    public String getCheckListCode() {
        return checkListCode;
    }

    public void setCheckListCode(String checkListCode) {
        this.checkListCode = checkListCode;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "QualityCheckTableRecordDto{" +
                "qualityCheckTableRecordID=" + qualityCheckTableRecordID +
                ", qualityCheckID=" + qualityCheckID +
                ", qualityCheckName='" + qualityCheckName + '\'' +
                ", checkListCode='" + checkListCode + '\'' +
                ", checkStatus='" + checkStatus + '\'' +
                ", description='" + description + '\'' +
                ", checkResult='" + checkResult + '\'' +
                ", attach='" + attach + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
