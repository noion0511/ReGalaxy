package com.regalaxy.phonesin.module.model;

import com.regalaxy.phonesin.module.model.entity.Ytwok;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class YtwokDto {
    private long ytwokId;

    private String originalFile;

    private String saveFile;

    @Builder
    public YtwokDto (long ytwokId, String saveFile, String originalFile){
        this.ytwokId = ytwokId;
        this.saveFile = saveFile;
        this.originalFile = originalFile;
    }
}
