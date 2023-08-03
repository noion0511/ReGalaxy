package com.regalaxy.phonesin.member.model;

import com.regalaxy.phonesin.back.model.BackDto;
import com.regalaxy.phonesin.back.model.entity.Back;
import com.regalaxy.phonesin.member.model.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private Long memberId;
    private String email;
    private String memberName;
    private String password;
    private String phoneNumber;
    private Boolean isCha;
    private Boolean isBlackList;
    private Boolean isDelete;
    private Boolean isManager;
    private LocalDateTime createdAt;

    public static MemberDto fromEntity(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail(member.getEmail());
        memberDto.setMemberName(member.getMemberName());
        memberDto.setPassword(member.getPassword());
        memberDto.setPhoneNumber(member.getPhoneNumber());
        memberDto.setIsCha(member.getIsCha());
        memberDto.setIsBlackList(member.getIsBlackList());
        memberDto.setIsDelete(member.getIsDelete());
        memberDto.setIsManager(member.getIsManager());
        memberDto.setCreatedAt(member.getCreatedAt());
        return memberDto;
    }
}