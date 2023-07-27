package com.regalaxy.phonesin.back.model;

import com.regalaxy.phonesin.back.model.entity.Back;
import com.regalaxy.phonesin.global.BaseTimeEntity;
import lombok.*;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class BackDto {
    private final int backStatus;
    private final String backDeliveryDate;
    private final LocalDateTime applyDate;
    private final String backDeliveryLocationType;
    private final String backDeliveryLocation;
    private final String backZipcode;
    private final String review;

    public BackDto(Back back) {
        this.backStatus = back.getBackStatus();
        this.applyDate = back.getApplyDate();
        this.backDeliveryDate = back.getBackDeliveryDate();
        this.backDeliveryLocationType = back.getBackDeliveryLocationType();
        this.backDeliveryLocation = back.getBackDeliveryLocation();
        this.backZipcode = back.getBackZipcode();
        this.review = back.getReview();
    }
}
