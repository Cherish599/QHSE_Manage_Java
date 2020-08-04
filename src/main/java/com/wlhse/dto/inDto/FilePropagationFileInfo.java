package com.wlhse.dto.inDto;

/**
 * Description:
 * Author:Coco
 * create:2020-08-04 5:59 PM
 **/
public class FilePropagationFileInfo {
    private Long id;
    private int propagationId;
    private String filePath;
    private String originName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPropagationId() {
        return propagationId;
    }

    public void setPropagationId(int propagationId) {
        this.propagationId = propagationId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }
}
