package com.wlhse.dto;

import com.wlhse.dto.getDto.BaseGetDto;

/**
 * @author tobing QQ:652916578
 * 【质量】隐患接口
 */
public class QualityDangerRecordDto extends BaseGetDto {
    private Integer id;
    private Integer checkId;
    private Integer safeStaff_ID;
    private String safeStaff_Name;
    private String workItem;
    private String companyId;
    private String companyName;
    private String supervisionDate;
    private String type;
    private String description;
    private Integer status;
    private String solution;
    private String reformPersonID;
    private String reformPerson;
    private String limitDate;
    private String receptionDate;
    private String reformCase;
    private String reason;
    private Integer approve;
    private Integer ok;
    private Integer consequenceID;
    private Integer checkType;
    private String affix;
    private String affixName;
    private String checkName;
    private String updateTime;
    private String dataStatus;
    private String recordDate;
    private String rank;
    private String factorSource;
    private String profession;
    private String factorHSE;
    private String factorDepartment;
    private String consequence;
    private String location;
    private String affix1;
    private String affix2;
    private String affix3;
    private String affix4;
    private Integer isUpload;
    private String keyID;
    private String quality_CheckType;
    private String quality_CheckCategory;
    private String quality_FileAudit_ID;
    private String quality_FileAuditRecord_ID;
    private String code;
    private String startDate;
    private String endDate;
    private String dangerSource;

    public String getDangerSource() {
        return dangerSource;
    }

    public void setDangerSource(String dangerSource) {
        this.dangerSource = dangerSource;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCheckId() {
        return checkId;
    }

    public void setCheckId(Integer checkId) {
        this.checkId = checkId;
    }

    public Integer getSafeStaff_ID() {
        return safeStaff_ID;
    }

    public void setSafeStaff_ID(Integer safeStaff_ID) {
        this.safeStaff_ID = safeStaff_ID;
    }

    public String getSafeStaff_Name() {
        return safeStaff_Name;
    }

    public void setSafeStaff_Name(String safeStaff_Name) {
        this.safeStaff_Name = safeStaff_Name;
    }

    public String getWorkItem() {
        return workItem;
    }

    public void setWorkItem(String workItem) {
        this.workItem = workItem;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSupervisionDate() {
        return supervisionDate;
    }

    public void setSupervisionDate(String supervisionDate) {
        this.supervisionDate = supervisionDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getReformPersonID() {
        return reformPersonID;
    }

    public void setReformPersonID(String reformPersonID) {
        this.reformPersonID = reformPersonID;
    }

    public String getReformPerson() {
        return reformPerson;
    }

    public void setReformPerson(String reformPerson) {
        this.reformPerson = reformPerson;
    }

    public String getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(String limitDate) {
        this.limitDate = limitDate;
    }

    public String getReceptionDate() {
        return receptionDate;
    }

    public void setReceptionDate(String receptionDate) {
        this.receptionDate = receptionDate;
    }

    public String getReformCase() {
        return reformCase;
    }

    public void setReformCase(String reformCase) {
        this.reformCase = reformCase;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getApprove() {
        return approve;
    }

    public void setApprove(Integer approve) {
        this.approve = approve;
    }

    public Integer getOk() {
        return ok;
    }

    public void setOk(Integer ok) {
        this.ok = ok;
    }

    public Integer getConsequenceID() {
        return consequenceID;
    }

    public void setConsequenceID(Integer consequenceID) {
        this.consequenceID = consequenceID;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public String getAffix() {
        return affix;
    }

    public void setAffix(String affix) {
        this.affix = affix;
    }

    public String getAffixName() {
        return affixName;
    }

    public void setAffixName(String affixName) {
        this.affixName = affixName;
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getFactorSource() {
        return factorSource;
    }

    public void setFactorSource(String factorSource) {
        this.factorSource = factorSource;
    }


    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getFactorHSE() {
        return factorHSE;
    }

    public void setFactorHSE(String factorHSE) {
        this.factorHSE = factorHSE;
    }

    public String getFactorDepartment() {
        return factorDepartment;
    }

    public void setFactorDepartment(String factorDepartment) {
        this.factorDepartment = factorDepartment;
    }

    public String getConsequence() {
        return consequence;
    }

    public void setConsequence(String consequence) {
        this.consequence = consequence;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAffix1() {
        return affix1;
    }

    public void setAffix1(String affix1) {
        this.affix1 = affix1;
    }

    public String getAffix2() {
        return affix2;
    }

    public void setAffix2(String affix2) {
        this.affix2 = affix2;
    }

    public String getAffix3() {
        return affix3;
    }

    public void setAffix3(String affix3) {
        this.affix3 = affix3;
    }

    public String getAffix4() {
        return affix4;
    }

    public void setAffix4(String affix4) {
        this.affix4 = affix4;
    }

    public Integer getIsUpload() {
        return isUpload;
    }

    public void setIsUpload(Integer isUpload) {
        this.isUpload = isUpload;
    }

    public String getKeyID() {
        return keyID;
    }

    public void setKeyID(String keyID) {
        this.keyID = keyID;
    }

    public String getQuality_CheckType() {
        return quality_CheckType;
    }

    public void setQuality_CheckType(String quality_CheckType) {
        this.quality_CheckType = quality_CheckType;
    }

    public String getQuality_CheckCategory() {
        return quality_CheckCategory;
    }

    public void setQuality_CheckCategory(String quality_CheckCategory) {
        this.quality_CheckCategory = quality_CheckCategory;
    }

    public String getQuality_FileAudit_ID() {
        return quality_FileAudit_ID;
    }

    public void setQuality_FileAudit_ID(String quality_FileAudit_ID) {
        this.quality_FileAudit_ID = quality_FileAudit_ID;
    }

    public String getQuality_FileAuditRecord_ID() {
        return quality_FileAuditRecord_ID;
    }

    public void setQuality_FileAuditRecord_ID(String quality_FileAuditRecord_ID) {
        this.quality_FileAuditRecord_ID = quality_FileAuditRecord_ID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
