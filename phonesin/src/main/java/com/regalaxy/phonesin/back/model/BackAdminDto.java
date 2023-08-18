package com.regalaxy.phonesin.back.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class BackAdminDto {
    private Long backId;
    private int backStatus;
    private LocalDateTime createAt;
    private String review;
}
