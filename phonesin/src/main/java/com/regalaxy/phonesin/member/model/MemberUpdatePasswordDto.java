package com.regalaxy.phonesin.member.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberUpdatePasswordDto {
    private String email;
    private String oldPassword;
    private String newPassword;
}
