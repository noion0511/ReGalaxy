package com.regalaxy.phonesin.module.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class YtwokDto {
    private long ytwokId;

    private String originalFile;

    private String saveFile;

    private String contentType;

    @Builder
    public YtwokDto (long ytwokId, String saveFile, String originalFile, String contentType){
        this.ytwokId = ytwokId;
        this.saveFile = saveFile;
        this.originalFile = originalFile;
        this.contentType = contentType;
    }
}
