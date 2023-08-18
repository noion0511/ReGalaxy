package com.regalaxy.phonesin.member.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailVerificationConfirmationDto {
    private String email;
    private String code;
}
