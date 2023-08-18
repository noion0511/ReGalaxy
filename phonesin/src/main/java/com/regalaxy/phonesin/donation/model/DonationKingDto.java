package com.regalaxy.phonesin.donation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.regalaxy.phonesin.donation.model.entity.Donation;
import com.regalaxy.phonesin.member.model.entity.Member;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@Getter
public class DonationKingDto {

    private String memberName;

    private Long donationCount;

    public DonationKingDto(String memberName, Long donationCount) {
        this.memberName = memberName;
        this.donationCount = donationCount;
    }
}
