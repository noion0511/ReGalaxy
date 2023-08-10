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
public class BackUserDto {
    private Long backId;
    private Long rentalId;
    private Long phoneId;
    private Long memberId;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate backDeliveryDate;
    private String backDeliveryLocationType;
    private String backDeliveryLocation;
    private String review;

    // Entity를 Dto로 바꾸는 메서드
    // 혹시 builder 사용할 수 있으면 바꿔보기!!
    public static BackUserDto fromEntity(Back back) {
        BackUserDto backUserDto = new BackUserDto();
        backUserDto.setBackDeliveryDate(back.getBackDeliveryDate());
        backUserDto.setBackDeliveryLocationType(back.getBackDeliveryLocationType());
        backUserDto.setBackDeliveryLocation(back.getBackDeliveryLocation());
        backUserDto.setReview(back.getReview());
        return backUserDto;
    }

    // Entity에 Rental을 build
    public Back toEntity(Rental rental){
        return Back.builder()
                .rental(rental)
                .phoneId(this.phoneId)
                .backDeliveryDate(this.backDeliveryDate)
                .backDeliveryLocationType(this.backDeliveryLocationType)
                .backDeliveryLocation(this.backDeliveryLocation)
                .review(this.review)
                .build();
    }

    public Long getMemberId(Back back) {
        return back.getRental().getMember().getMemberId();
    }
}