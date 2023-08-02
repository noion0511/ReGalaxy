package com.regalaxy.phonesin.donation.model.dto;

import com.regalaxy.phonesin.donation.model.entity.Donation;
import com.regalaxy.phonesin.member.model.entity.Member;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@ApiModel(value = "기기 기증서 List DTO", description = "기기 기증서 List DTO")
public class DonationListDto {

    @ApiModelProperty(value = "기증서 ID")
    private long donation_id;

    @ApiModelProperty(value = "기증 멤버 ID")
    private long member_id;

    @ApiModelProperty(value = "기증 상태")
    private int donation_status;

    @ApiModelProperty(value = "기증 날짜")
    private LocalDateTime donation_created_at;

    @ApiModelProperty(value = "기증 배송 신청 날짜")
    private String donation_delivery_date;

    @ApiModelProperty(value = "기증 배송 장소 타입")
    private String donation_delivery_location_type;

    @ApiModelProperty(value = "기증 배송 장소")
    private String donation_delivery_location;

    @Builder
    public DonationListDto(Donation donation){
        this.donation_id = donation.getDonationId();
        this.member_id = donation.getMember().getMemberId();
        this.donation_status = donation.getDonationStatus();
        this.donation_created_at = donation.getCreatedAt();
        this.donation_delivery_date = donation.getDonationDeliveryDate();
        this.donation_delivery_location_type = donation.getDonationDeliveryLocationType();
        this.donation_delivery_location = donation.getDonationDeliveryLocation();
    }

    public Donation toEntity(Member member) throws Exception {
        return Donation.builder()
                .member(member)
                .donation_status(this.donation_status)
                .donation_delivery_date(this.donation_delivery_date)
                .donation_delivery_location_type(this.donation_delivery_location_type)
                .donation_delivery_location(this.donation_delivery_location)
                .build();
    }
}