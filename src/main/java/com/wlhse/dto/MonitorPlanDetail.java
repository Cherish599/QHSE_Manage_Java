package com.wlhse.dto;

import com.alibaba.excel.annotation.ExcelProperty;

public class MonitorPlanDetail {

    private int monitorPlanDetailID;
    private int monitorPlanID;
    private String no;
    @ExcelProperty("设备编号")
    private String deviceNo;
    @ExcelProperty("自编号")
    private String myNo;
    @ExcelProperty("项目名称")
    private String projectName;
    @ExcelProperty("负责人")
    private String charger;
    @ExcelProperty("电话")
    private String tel;
    @ExcelProperty("基层单位")
    private String companyName;

    private String condition;

    public MonitorPlanDetail() {
    }

    public MonitorPlanDetail(int monitorPlanDetailID, int monitorPlanID, String no, String deviceNo, String myNo, String projectName, String charger, String tel, String companyName, String condition) {
        this.monitorPlanDetailID = monitorPlanDetailID;
        this.monitorPlanID = monitorPlanID;
        this.no = no;
        this.deviceNo = deviceNo;
        this.myNo = myNo;
        this.projectName = projectName;
        this.charger = charger;
        this.tel = tel;
        this.companyName = companyName;
        this.condition = condition;
    }

    public int getMonitorPlanDetailID() {
        return monitorPlanDetailID;
    }

    public void setMonitorPlanDetailID(int monitorPlanDetailID) {
        this.monitorPlanDetailID = monitorPlanDetailID;
    }

    public int getMonitorPlanID() {
        return monitorPlanID;
    }

    public void setMonitorPlanID(int monitorPlanID) {
        this.monitorPlanID = monitorPlanID;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getMyNo() {
        return myNo;
    }

    public void setMyNo(String myNo) {
        this.myNo = myNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCharger() {
        return charger;
    }

    public void setCharger(String charger) {
        this.charger = charger;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "MonitorPlanDetail{" +
                "monitorPlanDetailID=" + monitorPlanDetailID +
                ", monitorPlanID=" + monitorPlanID +
                ", no='" + no + '\'' +
                ", deviceNo='" + deviceNo + '\'' +
                ", myNo='" + myNo + '\'' +
                ", projectName='" + projectName + '\'' +
                ", charger='" + charger + '\'' +
                ", tel='" + tel + '\'' +
                ", companyName='" + companyName + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
