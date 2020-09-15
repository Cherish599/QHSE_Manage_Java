package com.wlhse.dto;

public class QualityCheckDto {
    Integer qualityCheckID;
    String taskType;
    String checkListCode;//选择的所有表节点
    String checkedCompanyName;
    String checkedCompanyCode;
    String group;
    String workProject;
    String responsiCompanyName;
    String responsiCompanyCode;
    Integer responsePersonID;
    String responsePersonName;
    String checkDate;
    Integer checkPersonID;
    String checkPerson;
    String checkBasis;
    String contractor;
    String owner;
    String projectName;
    String checkProject;
    String execStd;
    String isPush;
    String checkListName;

    public String getCheckListName() {
        return checkListName;
    }

    public void setCheckListName(String checkListName) {
        checkListName = checkListName;
    }

    public Integer getQualityCheckID() {
        return qualityCheckID;
    }

    public void setQualityCheckID(Integer qualityCheckID) {
        this.qualityCheckID = qualityCheckID;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getCheckListCode() {
        return checkListCode;
    }

    public void setCheckListCode(String checkListCode) {
        this.checkListCode = checkListCode;
    }

    public String getCheckedCompanyName() {
        return checkedCompanyName;
    }

    public void setCheckedCompanyName(String checkedCompanyName) {
        this.checkedCompanyName = checkedCompanyName;
    }

    public String getCheckedCompanyCode() {
        return checkedCompanyCode;
    }

    public void setCheckedCompanyCode(String checkedCompanyCode) {
        this.checkedCompanyCode = checkedCompanyCode;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getWorkProject() {
        return workProject;
    }

    public void setWorkProject(String workProject) {
        this.workProject = workProject;
    }

    public String getResponsiCompanyName() {
        return responsiCompanyName;
    }

    public void setResponsiCompanyName(String responsiCompanyName) {
        this.responsiCompanyName = responsiCompanyName;
    }

    public String getResponsiCompanyCode() {
        return responsiCompanyCode;
    }

    public void setResponsiCompanyCode(String responsiCompanyCode) {
        this.responsiCompanyCode = responsiCompanyCode;
    }

    public Integer getResponsePersonID() {
        return responsePersonID;
    }

    public void setResponsePersonID(Integer responsePersonID) {
        this.responsePersonID = responsePersonID;
    }

    public String getResponsePersonName() {
        return responsePersonName;
    }

    public void setResponsePersonName(String responsePersonName) {
        this.responsePersonName = responsePersonName;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public Integer getCheckPersonID() {
        return checkPersonID;
    }

    public void setCheckPersonID(Integer checkPersonID) {
        this.checkPersonID = checkPersonID;
    }

    public String getCheckPerson() {
        return checkPerson;
    }

    public void setCheckPerson(String checkPerson) {
        this.checkPerson = checkPerson;
    }

    public String getCheckBasis() {
        return checkBasis;
    }

    public void setCheckBasis(String checkBasis) {
        this.checkBasis = checkBasis;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCheckProject() {
        return checkProject;
    }

    public void setCheckProject(String checkProject) {
        this.checkProject = checkProject;
    }

    public String getExecStd() {
        return execStd;
    }

    public void setExecStd(String execStd) {
        this.execStd = execStd;
    }

    public String getIsPush() {
        return isPush;
    }

    public void setIsPush(String isPush) {
        this.isPush = isPush;
    }

    @Override
    public String toString() {
        return "QualityCheckDto{" +
                "qualityCheckID=" + qualityCheckID +
                ", taskType='" + taskType + '\'' +
                ", checkListCode='" + checkListCode + '\'' +
                ", checkedCompanyName='" + checkedCompanyName + '\'' +
                ", checkedCompanyCode='" + checkedCompanyCode + '\'' +
                ", group='" + group + '\'' +
                ", workProject='" + workProject + '\'' +
                ", responsiCompanyName='" + responsiCompanyName + '\'' +
                ", responsiCompanyCode='" + responsiCompanyCode + '\'' +
                ", responsePersonID=" + responsePersonID +
                ", responsePersonName='" + responsePersonName + '\'' +
                ", checkDate='" + checkDate + '\'' +
                ", checkPersonID=" + checkPersonID +
                ", checkPerson='" + checkPerson + '\'' +
                ", checkBasis='" + checkBasis + '\'' +
                ", contractor='" + contractor + '\'' +
                ", owner='" + owner + '\'' +
                ", projectName='" + projectName + '\'' +
                ", checkProject='" + checkProject + '\'' +
                ", execStd='" + execStd + '\'' +
                ", isPush='" + isPush + '\'' +
                ", checkListName='" + checkListName + '\'' +
                '}';
    }
}
