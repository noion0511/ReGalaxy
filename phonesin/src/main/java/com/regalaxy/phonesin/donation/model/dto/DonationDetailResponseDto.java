package com.regalaxy.phonesin.donation.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.regalaxy.phonesin.donation.model.entity.Donation;
import com.regalaxy.phonesin.member.model.dto.MemberResponseDto;
import com.regalaxy.phonesin.member.model.entity.Member;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@ApiModel(value = "기기 기증서 List DTO", description = "기기 기증서 List DTO")
public class DonationDetailResponseDto {

    @ApiModelProperty(value = "기증서 ID")
    private long donationId;

    @ApiModelProperty(value = "기증 멤버")
    private MemberResponseDto memberResponseDto;

    @ApiModelProperty(value = "기증 상태")
    private int donationStatus;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty(value = "기증 날짜")
    private LocalDateTime donationCreatedAt;

    @ApiModelProperty(value = "기증 배송 신청 날짜")
    private String donationDeliveryDate;

    @ApiModelProperty(value = "기증 배송 장소 타입")
    private String donationDeliveryLocationType;

    @ApiModelProperty(value = "기증 배송 장소")
    private String donationDeliveryLocation;

    @Builder
    public DonationDetailResponseDto(Donation donation, Member member){
        this.donationId = donation.getDonationId();
        this.memberResponseDto = MemberResponseDto.builder().member(member).build();
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
