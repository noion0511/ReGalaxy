package com.regalaxy.phonesin.back.model;

import com.regalaxy.phonesin.back.model.entity.Back;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@ToString
public class BackDto {
    private Long backId;
    private Long rentalId;
    private int backStatus;
    private LocalDate backDeliveryDate;
    private LocalDateTime applyDate;
    private String backDeliveryLocationType;
    private String backDeliveryLocation;
    private String backZipcode;
    private String review;

    public static BackDto toBackDto(Back back) {
        BackDto backDto = new BackDto();
        backDto.setBackId(back.getBackId());
        backDto.setRentalId(back.getRentalId());
        backDto.setBackStatus(back.getBackStatus());
        backDto.setBackDeliveryDate(back.getBackDeliveryDate());
        backDto.setApplyDate(back.getApplyDate());
        backDto.setBackDeliveryLocationType(back.getBackDeliveryLocationType());
        backDto.setBackDeliveryLocation(back.getBackDeliveryLocation());
        backDto.setBackZipcode(back.getBackZipcode());
        backDto.setReview(back.getReview());

        return backDto;
    }
}
