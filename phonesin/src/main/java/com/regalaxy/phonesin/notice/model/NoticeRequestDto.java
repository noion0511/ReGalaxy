package com.regalaxy.phonesin.notice.model;

import lombok.Getter;

@Getter
public class NoticeRequestDto {
    private String title;
    private Integer noticeType;

    public NoticeRequestDto(String title, Integer noticeType) {
        this.title = title;
        this.noticeType = noticeType;
    }
}
