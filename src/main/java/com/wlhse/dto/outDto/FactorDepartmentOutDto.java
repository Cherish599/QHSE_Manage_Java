package com.wlhse.dto.outDto;

public class FactorDepartmentOutDto {

    private Integer id;

    private String factorDepartmentCode;

    private String factorDepartmentName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFactorDepartmentCode() {
        return factorDepartmentCode;
    }

    public void setFactorDepartmentCode(String factorDepartmentCode) {
        this.factorDepartmentCode = factorDepartmentCode;
    }

    public String getFactorDepartmentName() {
        return factorDepartmentName;
    }

    public void setFactorDepartmentName(String factorDepartmentName) {
        this.factorDepartmentName = factorDepartmentName;
    }

    @Override
    public String toString() {
        return "FactorDepartmentOutDto{" +
                "id=" + id +
                ", factorDepartmentCode='" + factorDepartmentCode + '\'' +
                ", factorDepartmentName='" + factorDepartmentName + '\'' +
                '}';
    }
}
