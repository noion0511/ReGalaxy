package com.regalaxy.phonesin.donation.model;

import com.regalaxy.phonesin.donation.model.entity.Donation;
import com.regalaxy.phonesin.member.model.entity.Member;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@ApiModel(value = "기기 기증서 DTO", description = "기기 기증서 DTO")
public class DonationRequestDto {
    private long donationId; // 필요없음

    private long memberId; // jwt에서 날라옴

    private int donationStatus;

    private String donationDeliveryDate;

    private String donationDeliveryLocationType;

    private String donationDeliveryLocation;

    public Donation toEntity(Member member) throws Exception {
        return Donation.builder()
                .member(member)
                .donation_status(this.donationStatus)
                .donation_delivery_date(this.donationDeliveryDate)
                .donation_delivery_location_type(this.donationDeliveryLocationType)
                .donation_delivery_location(this.donationDeliveryLocation)
                .build();
    }
}
