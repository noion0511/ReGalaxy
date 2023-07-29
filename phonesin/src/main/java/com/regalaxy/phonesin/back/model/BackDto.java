package com.regalaxy.phonesin.back.model;

import com.regalaxy.phonesin.back.model.entity.Back;
import com.regalaxy.phonesin.rental.model.entity.Rental;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@RequiredArgsConstructor
public class BackDto {
    private Long backId;
    private Long rentalId;
    private int backStatus;
    private LocalDate backDeliveryDate;
    private String backDeliveryLocationType;
    private String backDeliveryLocation;
    private String backZipcode;
    private String review;
    private LocalDateTime createdAt;

    public static BackDto fromEntity(Back back) {
        BackDto backDto = new BackDto();
        backDto.setBackId(back.getBackId());
        backDto.setRentalId(back.getRental().getRental_id());
        backDto.setBackStatus(back.getBackStatus());
        backDto.setBackDeliveryDate(back.getBackDeliveryDate());
        backDto.setBackDeliveryLocationType(back.getBackDeliveryLocationType());
        backDto.setBackDeliveryLocation(back.getBackDeliveryLocation());
        backDto.setBackZipcode(back.getBackZipcode());
        backDto.setReview(back.getReview());
        backDto.setCreatedAt(back.getCreatedAt());
        return backDto;
    }

    public Back toEntity(Rental rental){
        return Back.builder()
                .rental(rental)
                .backStatus(this.backStatus)
                .backDeliveryDate(this.backDeliveryDate)
                .backDeliveryLocationType(this.backDeliveryLocationType)
                .backDeliveryLocation(this.backDeliveryLocation)
                .backZipcode(this.backZipcode)
                .review(this.review)
                .build();
    }
}
