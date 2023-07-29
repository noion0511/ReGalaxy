package com.regalaxy.phonesin.member.model.dto;

import com.regalaxy.phonesin.donation.model.entity.Donation;
import com.regalaxy.phonesin.member.model.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberResponseDto {
    private Long member_id;

    private String email;
    private String member_name;
    private String phone_number;
    private Boolean isCha;
    private Boolean isBlackList;
    private Boolean isDelete;
    private Boolean isManager;

    @Builder
    public MemberResponseDto(Member member) {
        this.member_id = member.getMember_id();
        this.email = member.getEmail();
        this.member_name = member.getMember_name();
        this.phone_number = member.getPhone_number();
        this.isCha = member.getIsCha();
        this.isBlackList = member.getIsBlackList();
        this.isDelete = member.getIsDelete();
        this.isManager = member.getIsManager();
    }
}
