package com.regalaxy.phonesin.donation.model;

import com.regalaxy.phonesin.donation.model.entity.Donation;
import com.regalaxy.phonesin.member.model.entity.Member;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@ApiModel(value = "기기 기증서 DTO", description = "기기 기증서 DTO")
public class DonationRequestDto {

    private int donationStatus;

    private LocalDate donationDeliveryDate;

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
