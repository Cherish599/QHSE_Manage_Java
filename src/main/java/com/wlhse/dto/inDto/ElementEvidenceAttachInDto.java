package com.wlhse.dto.inDto;

public class ElementEvidenceAttachInDto {

    private Integer id;//要素id

    private Integer evidenceID;//证据id

    private String code;

    private String evidenceDescription;

    private String attachID;//附件id

    private String attachDescrption;

    private String attach;

    private String uploadTime;

    private Integer checkStaffID;

    private String checkStaffName;

    private Integer approverStaffID;

    private String approverStaffName;

    private String url;//附件地址前缀

    @Override
    public String toString() {
        return "ElementEvidenceAttachInDto{" +
                "id=" + id +
                ", evidenceID=" + evidenceID +
                ", code='" + code + '\'' +
                ", evidenceDescription='" + evidenceDescription + '\'' +
                ", attachID='" + attachID + '\'' +
                ", attachDescrption='" + attachDescrption + '\'' +
                ", attach='" + attach + '\'' +
                ", uploadTime='" + uploadTime + '\'' +
                ", checkStaffID=" + checkStaffID +
                ", checkStaffName='" + checkStaffName + '\'' +
                ", approverStaffID=" + approverStaffID +
                ", approverStaffName='" + approverStaffName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEvidenceID() {
        return evidenceID;
    }

    public void setEvidenceID(Integer evidenceID) {
        this.evidenceID = evidenceID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEvidenceDescription() {
        return evidenceDescription;
    }

    public void setEvidenceDescription(String evidenceDescription) {
        this.evidenceDescription = evidenceDescription;
    }

    public String getAttachID() {
        return attachID;
    }

    public void setAttachID(String attachID) {
        this.attachID = attachID;
    }

    public String getAttachDescrption() {
        return attachDescrption;
    }

    public void setAttachDescrption(String attachDescrption) {
        this.attachDescrption = attachDescrption;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getCheckStaffID() {
        return checkStaffID;
    }

    public void setCheckStaffID(Integer checkStaffID) {
        this.checkStaffID = checkStaffID;
    }

    public String getCheckStaffName() {
        return checkStaffName;
    }

    public void setCheckStaffName(String checkStaffName) {
        this.checkStaffName = checkStaffName;
    }

    public Integer getApproverStaffID() {
        return approverStaffID;
    }

    public void setApproverStaffID(Integer approverStaffID) {
        this.approverStaffID = approverStaffID;
    }

    public String getApproverStaffName() {
        return approverStaffName;
    }

    public void setApproverStaffName(String approverStaffName) {
        this.approverStaffName = approverStaffName;
    }
}
