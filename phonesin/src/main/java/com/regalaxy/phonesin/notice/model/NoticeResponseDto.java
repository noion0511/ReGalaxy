package com.regalaxy.phonesin.notice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.regalaxy.phonesin.notice.model.entity.Notice;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NoticeResponseDto {
    private Long NoticeId;
    private String title;
    private String posterUrl;
    private Integer status;
    private Integer noticeType;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime updatedAt;

    public NoticeResponseDto(Notice notice) {
        this.NoticeId = notice.getNoticeId();
        this.title = notice.getTitle();
        this.posterUrl = notice.getPosterUrl();
        this.status = notice.getStatus();
        this.noticeType = notice.getNoticeType();
        this.createdAt = notice.getCreatedAt();
        this.updatedAt = notice.getUpdatedAt();
    }
}
