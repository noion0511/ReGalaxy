package com.regalaxy.phonesin.member;

import com.regalaxy.phonesin.donation.model.entity.Donation;
import com.regalaxy.phonesin.member.model.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDto {
    private Long memberId;

    private String email;
    private String memberName;
    private String phoneNumber;
    private Boolean isCha;
    private Boolean isBlackList;
    private Boolean isDelete;
    private Boolean isManager;

    @Builder
    public MemberResponseDto(Member member) {
        this.memberId = member.getMemberId();
        this.email = member.getEmail();
        this.memberName = member.getMemberName();
        this.phoneNumber = member.getPhoneNumber();
        this.isCha = member.getIsCha();
        this.isBlackList = member.getIsBlackList();
        this.isDelete = member.getIsDelete();
        this.isManager = member.getIsManager();
    }
}
