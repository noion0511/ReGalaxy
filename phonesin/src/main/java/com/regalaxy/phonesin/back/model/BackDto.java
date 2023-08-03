package com.regalaxy.phonesin.back.model;

import com.regalaxy.phonesin.back.model.entity.Back;
import com.regalaxy.phonesin.phone.model.entity.Phone;
import com.regalaxy.phonesin.rental.model.entity.Rental;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@RequiredArgsConstructor
public class BackDto {
    private Long backId;
    private Long rentalId;
    private Long phoneId;
    private int backStatus;
    private LocalDate backDeliveryDate;
    private String backDeliveryLocationType;
    private String backDeliveryLocation;
    private String backZipcode;
    private String review;
    private LocalDateTime createdAt;

    // Entity를 Dto로 바꾸는 메서드
    // 혹시 builder 사용할 수 있으면 바꿔보기!!
    public static BackDto fromEntity(Back back) {
        BackDto backDto = new BackDto();
        backDto.setBackStatus(back.getBackStatus());
        backDto.setBackDeliveryDate(back.getBackDeliveryDate());
        backDto.setBackDeliveryLocationType(back.getBackDeliveryLocationType());
        backDto.setBackDeliveryLocation(back.getBackDeliveryLocation());
        backDto.setBackZipcode(back.getBackZipcode());
        backDto.setReview(back.getReview());
        return backDto;
    }

    // Entity에 Rental을 build
    public Back toEntity(Rental rental, Phone phone){
        return Back.builder()
                .rental(rental)
                .phone(phone)
                .backStatus(this.backStatus)
                .backDeliveryDate(this.backDeliveryDate)
                .backDeliveryLocationType(this.backDeliveryLocationType)
                .backDeliveryLocation(this.backDeliveryLocation)
                .backZipcode(this.backZipcode)
                .review(this.review)
                .build();
    }
}