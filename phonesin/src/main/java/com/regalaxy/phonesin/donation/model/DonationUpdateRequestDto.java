package com.regalaxy.phonesin.donation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.regalaxy.phonesin.donation.model.entity.Donation;
import com.regalaxy.phonesin.member.model.entity.Member;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.time.LocalDate;


@Getter
@ApiModel(value = "기기 기증서 수정 DTO", description = "기기 기증서 수정 DTO")
public class DonationUpdateRequestDto{

    @ApiModelProperty(value = "상태 코드 (1:신청 - 2:승인 - 3:수거 - 4:완료)")
    private Integer donationStatus;

    @ApiModelProperty(value = "날짜(yyyy/MM/dd)")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate donationDeliveryDate;

    @ApiModelProperty(value = "배달 / 대리점")
    private String donationDeliveryLocationType;

    @ApiModelProperty(value = "주소")
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
