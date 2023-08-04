package com.regalaxy.phonesin.donation.model;

import com.regalaxy.phonesin.donation.model.entity.Donation;
import com.regalaxy.phonesin.member.MemberResponseDto;
import com.regalaxy.phonesin.member.model.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DonationDetailResponseDto extends DonationResponseDto{

    private MemberResponseDto memberResponseDto;

    public DonationDetailResponseDto(Donation donation){
        super(donation);
        this.memberResponseDto = MemberResponseDto.builder().member(donation.getMember()).build();
    }

    public Donation toEntity(Member member) throws Exception {
        return Donation.builder()
                .member(member)
                .build();
    }
}
