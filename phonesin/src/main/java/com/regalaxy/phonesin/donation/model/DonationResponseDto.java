package com.regalaxy.phonesin.donation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.regalaxy.phonesin.donation.model.entity.Donation;
import com.regalaxy.phonesin.member.model.entity.Member;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
public class DonationResponseDto {

    private long donationId;

    private long memberId;

    private int donationStatus;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime donationCreatedAt;

    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate donationDeliveryDate;

    private String donationDeliveryLocationType;

    private String donationDeliveryLocation;

    public DonationResponseDto(Donation donation){
        this.donationId = donation.getDonationId();
        this.memberId = donation.getMember().getMemberId();
        this.donationStatus = donation.getDonationStatus();
        this.donationCreatedAt = donation.getCreatedAt();
        this.donationDeliveryDate = donation.getDonationDeliveryDate();
        this.donationDeliveryLocationType = donation.getDonationDeliveryLocationType();
        this.donationDeliveryLocation = donation.getDonationDeliveryLocation();
    }

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
