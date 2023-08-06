package com.regalaxy.phonesin.donation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.regalaxy.phonesin.donation.model.entity.Donation;
import com.regalaxy.phonesin.member.model.MemberAdminDto;
import com.regalaxy.phonesin.member.model.MemberUserDto;
import com.regalaxy.phonesin.member.model.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DonationDetailResponseDto extends DonationResponseDto{

    private MemberUserDto memberUserDto;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime applyDate;

    public DonationDetailResponseDto(Donation donation){
        super(donation);
        Member member = donation.getMember();
        this.memberUserDto = MemberUserDto.builder()
                .memberName(member.getMemberName())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .isCha(member.getIsCha())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();;
    }
}
