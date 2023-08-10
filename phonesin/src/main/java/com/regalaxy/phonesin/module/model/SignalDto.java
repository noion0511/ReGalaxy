package com.regalaxy.phonesin.module.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignalDto {
    private String type;    // 메시지 타입 (예: "offer", "answer", "candidate" 등)
    private String content; // 메시지 내용
    private String roomId;  // 방의 식별자

    // 생성자, getter, setter 등의 메서드

    // 생성자
    public SignalDto(String type, String content, String roomId) {
        this.type = type;
        this.content = content;
        this.roomId = roomId;
    }
}
