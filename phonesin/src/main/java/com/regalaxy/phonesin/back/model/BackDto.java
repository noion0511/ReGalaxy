package com.regalaxy.phonesin.back.model;

import com.regalaxy.phonesin.back.model.entity.Back;
import com.regalaxy.phonesin.global.BaseTimeEntity;
import com.regalaxy.phonesin.rental.model.entity.Rental;
import lombok.*;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class BackDto {
    private Long backId;
    private Long rentalId;
    private int backStatus;
    private String backDeliveryDate;
//    private LocalDateTime applyDate;
    private String backDeliveryLocationType;
    private String backDeliveryLocation;
    private String backZipcode;
    private String review;
    private LocalDateTime createdAt;

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
