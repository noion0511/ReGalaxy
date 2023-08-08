package com.regalaxy.phonesin.member.model;

import com.regalaxy.phonesin.member.model.entity.Member;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignUpDto {
    private String email;
    private String memberName;
    private String password;
    private String phoneNumber;

    public MemberSignUpDto(Member member) {
        this.email = member.getEmail();
        this.memberName = member.getMemberName();
        this.phoneNumber = member.getPhoneNumber();
        this.password = member.getPassword();
    }
}