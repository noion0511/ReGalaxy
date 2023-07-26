package com.regalaxy.phonesin.donation.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@ApiModel(value = "기기 기증서 DTO", description = "기기 기증서 DTO")
public class DonationDto {
    @ApiModelProperty(value = "기증서 ID")
    private int donation_id;

    @ApiModelProperty(value = "기증 멤버 ID")
    private int member_id;

    @ApiModelProperty(value = "기증 상태")
    private int donation_status;

    @ApiModelProperty(value = "기증 날짜")
    private String donation_created_at;

    @ApiModelProperty(value = "배송 신청 날짜")
    private String donation_delivery_date;

    @ApiModelProperty(value = "배송 장소 타입")
    private String donation_delivery_location_type;

    @ApiModelProperty(value = "배송 장소")
    private String donation_delivery_location;
}
