package com.wlhse.dto;


public class MonitorPlan {
    private int monitorPlanID;
    private String startDate;
    private String endDate;
    private String planName;
    private int planPersonID;
    private String planPersonName;

    public int getMonitorPlanID() {
        return monitorPlanID;
    }

    public void setMonitorPlanID(int monitorPlanID) {
        this.monitorPlanID = monitorPlanID;
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

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public int getPlanPersonID() {
        return planPersonID;
    }

    public void setPlanPersonID(int planPersonID) {
        this.planPersonID = planPersonID;
    }

    public String getPlanPersonName() {
        return planPersonName;
    }

    public void setPlanPersonName(String planPersonName) {
        this.planPersonName = planPersonName;
    }

    @Override
    public String toString() {
        return "MonitorPlan{" +
                "monitorPlanID=" + monitorPlanID +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", planName='" + planName + '\'' +
                ", planPersonID='" + planPersonID + '\'' +
                ", planPersonName='" + planPersonName + '\'' +
                '}';
    }
}