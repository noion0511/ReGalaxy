package com.regalaxy.phonesin.member.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.regalaxy.phonesin.member.model.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdateByUserDto {
    private String memberName;
    private String phoneNumber;
    private Boolean isCha;

    public MemberUpdateByUserDto(Member member) {
        this.memberName = member.getMemberName();
        this.phoneNumber = member.getPhoneNumber();
        this.isCha = member.getIsCha();
    }
}
