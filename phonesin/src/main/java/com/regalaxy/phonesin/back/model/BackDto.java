package com.regalaxy.phonesin.back.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.regalaxy.phonesin.back.model.entity.Back;
import com.regalaxy.phonesin.phone.model.entity.Phone;
import com.regalaxy.phonesin.rental.model.entity.Rental;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@RequiredArgsConstructor
public class BackDto {
    private Long rentalId;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate backDeliveryDate;
    private BackDeliveryLocationType backDeliveryLocationType;
    private String backDeliveryLocation;
    private String review;
    private Boolean phoneStatus;

    // Entity를 Dto로 바꾸는 메서드
    // 혹시 builder 사용할 수 있으면 바꿔보기!!
    public static BackDto fromEntity(Back back) {
        BackDto backDto = new BackDto();
        backDto.setBackDeliveryDate(back.getBackDeliveryDate());
        backDto.setBackDeliveryLocationType(back.getBackDeliveryLocationType());
        backDto.setBackDeliveryLocation(back.getBackDeliveryLocation());
        backDto.setReview(back.getReview());
        backDto.setPhoneStatus(back.getPhoneStatus());
        return backDto;
    }

    // Entity에 Rental을 build
    public Back toEntity(Rental rental){
        return Back.builder()
                .rental(rental)
                .backStatus(1)
                .backDeliveryDate(this.backDeliveryDate)
                .backDeliveryLocationType(this.backDeliveryLocationType)
                .backDeliveryLocation(this.backDeliveryLocation)
                .review(this.review)
                .phoneStatus(this.phoneStatus)
                .build();
    }

    public Long getMemberId(Back back) {
        return back.getRental().getMember().getMemberId();
    }
}