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
public class MemberAdminDto {
    private Long memberId;

    private String email;
    private String memberName;
    private String phoneNumber;
    private Boolean isCha;
    private Boolean isBlackList;
    private Boolean isDelete;
    private Boolean isManager;
    private String verificationCode;
    private Boolean isVerified;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updatedAt;

    @Builder
    public MemberAdminDto(Member member) {
        this.memberId = member.getMemberId();
        this.email = member.getEmail();
        this.memberName = member.getMemberName();
        this.phoneNumber = member.getPhoneNumber();
        this.isCha = member.getIsCha();
        this.isBlackList = member.getIsBlackList();
        this.isDelete = member.getIsDelete();
        this.isManager = member.getIsManager();
        this.createdAt = member.getCreatedAt();
        this.updatedAt = member.getUpdatedAt();
        this.verificationCode = member.getVerificationCode();
        this.isVerified = member.getIsVerified();
    }
}
