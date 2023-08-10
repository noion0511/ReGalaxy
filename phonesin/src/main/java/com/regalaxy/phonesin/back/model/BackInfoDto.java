package com.regalaxy.phonesin.back.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.regalaxy.phonesin.back.model.entity.Back;
import com.regalaxy.phonesin.phone.model.entity.Phone;
import com.regalaxy.phonesin.rental.model.entity.Rental;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class BackInfoDto {
    private Long backId;
    private Long phoneId;
    private String modelName;
    private int backStatus; // 반납 상태 : 신청(1), 승인(2), 수거완료(3), 상태확인(4)
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate backDeliveryDate;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime createdAt; // 신청서 작성 날짜(자동 생성)(BaseTimeEntity)
    private String backDeliveryLocation; // 1 배달의 경우 배달지 주소, 서비스센터 제출의 경우 서비스센터 주소
    private Boolean phoneStatus;

    public BackInfoDto(Back back) {
        this.backId = back.getBackId();
        this.phoneId = back.getRental().getPhone().getPhoneId();
        this.modelName = back.getRental().getPhone().getModel().getModelName();
        this.backStatus = back.getBackStatus();
        this.backDeliveryDate =back.getBackDeliveryDate();
        this.createdAt = back.getCreatedAt();
        this.backDeliveryLocation = back.getBackDeliveryLocation();
        this.phoneStatus = back.getPhoneStatus();
    }
}
