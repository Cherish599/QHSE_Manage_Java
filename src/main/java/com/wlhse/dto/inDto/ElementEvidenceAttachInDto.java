package com.wlhse.dto.inDto;

public class ElementEvidenceAttachInDto {

    private Integer id;

    private Integer qhseCompanyYearManagerSysElementEvidenceID;

    private String attachDescrption;

    private String attach;

    private String uploadTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQhseCompanyYearManagerSysElementEvidenceID() {
        return qhseCompanyYearManagerSysElementEvidenceID;
    }

    public void setQhseCompanyYearManagerSysElementEvidenceID(Integer qhseCompanyYearManagerSysElementEvidenceID) {
        this.qhseCompanyYearManagerSysElementEvidenceID = qhseCompanyYearManagerSysElementEvidenceID;
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
}
