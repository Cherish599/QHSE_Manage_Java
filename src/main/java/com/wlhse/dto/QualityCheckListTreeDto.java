package com.wlhse.dto;

import com.alibaba.fastjson.annotation.JSONType;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@JSONType(orders = {"checkListID","checkListName","checkListCode","attribute","parentName","isChildNode","status"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QualityCheckListTreeDto implements Cloneable{
    private Integer checkListID;
    private String checkListName;
    private String checkListCode;
    private String attribute;
    private String parentName;
    private String isChildNode;
    private String status;
    private String checkCategory;
    private String checkBasis;
    private String checkMethod;

    private List<QualityCheckListTreeDto> children;//=new LinkedList<>()

    public Integer getCheckListID() {
        return checkListID;
    }

    public void setCheckListID(Integer checkListID) {
        this.checkListID = checkListID;
    }

    public String getCheckListName() {
        return checkListName;
    }

    public void setCheckListName(String checkListName) {
        this.checkListName = checkListName;
    }

    public String getCheckListCode() {
        return checkListCode;
    }

    public void setCheckListCode(String checkListCode) {
        this.checkListCode = checkListCode;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getIsChildNode() {
        return isChildNode;
    }

    public void setIsChildNode(String isChildNode) {
        this.isChildNode = isChildNode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCheckCategory() {
        return checkCategory;
    }

    public void setCheckCategory(String checkCategory) {
        this.checkCategory = checkCategory;
    }

    public String getCheckBasis() {
        return checkBasis;
    }

    public void setCheckBasis(String checkBasis) {
        this.checkBasis = checkBasis;
    }

    public String getCheckMethod() {
        return checkMethod;
    }

    public void setCheckMethod(String checkMethod) {
        this.checkMethod = checkMethod;
    }

    public List<QualityCheckListTreeDto> getChildren() {
        return children;
    }

    public void setChildren(List<QualityCheckListTreeDto> children) {
        this.children = children;
    }


    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
