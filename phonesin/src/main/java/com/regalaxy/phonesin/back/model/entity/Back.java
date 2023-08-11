package com.regalaxy.phonesin.back.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.regalaxy.phonesin.back.model.BackAdminUpdateDto;
import com.regalaxy.phonesin.back.model.BackDeliveryLocationType;
import com.regalaxy.phonesin.back.model.BackDto;
import com.regalaxy.phonesin.back.model.BackUserDto;
import com.regalaxy.phonesin.global.BaseTimeEntity;
import com.regalaxy.phonesin.phone.model.entity.Phone;
import com.regalaxy.phonesin.rental.model.entity.Rental;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "back")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "back")
public class Back extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long backId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id", unique = true)
    private Rental rental;

    @Min(1)
    @Max(4)
    private int backStatus = 1; // 반납 상태 : 신청(1), 승인(2), 수거완료(3), 상태확인(4)
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate backDeliveryDate;
    @Enumerated(EnumType.STRING)
    private BackDeliveryLocationType backDeliveryLocationType; // 배달, 서비스센터 제출, 직접 제출
    private String backDeliveryLocation; // 1 배달의 경우 배달지 주소, 서비스센터 제출의 경우 서비스센터 주소
    private String review; // 사용 후기 또는 조기 반납 이유
    private Boolean phoneStatus; // 휴대폰 상태

    @Builder
    public Back(Rental rental, int backStatus, LocalDate backDeliveryDate, BackDeliveryLocationType backDeliveryLocationType, String backDeliveryLocation, String review, Long phoneId, Boolean phoneStatus) {
        this.backStatus = backStatus;
        this.backDeliveryDate = backDeliveryDate;
        this.backDeliveryLocationType = backDeliveryLocationType;
        this.backDeliveryLocation = backDeliveryLocation;
        this.review = review;
        this.rental = rental;
        this.phoneStatus = phoneStatus;
    }

    public void updateByAdmin(BackAdminUpdateDto backAdminUpdateDto) {
        this.backStatus = backAdminUpdateDto.getBackStatus();
        this.backDeliveryDate = backAdminUpdateDto.getBackDeliveryDate();
        this.backDeliveryLocationType = backAdminUpdateDto.getBackDeliveryLocationType();
        this.backDeliveryLocation = backAdminUpdateDto.getBackDeliveryLocation();
        this.review = backAdminUpdateDto.getReview();
        this.phoneStatus = backAdminUpdateDto.getPhoneStatus();
    }

    public void updateByUser(BackUserDto backUserDto, Rental rental) {
        this.backDeliveryDate = backUserDto.getBackDeliveryDate();
        this.backDeliveryLocationType = backUserDto.getBackDeliveryLocationType();
        this.backDeliveryLocation = backUserDto.getBackDeliveryLocation();
        this.review = backUserDto.getReview();
        this.rental = rental;
    }
}