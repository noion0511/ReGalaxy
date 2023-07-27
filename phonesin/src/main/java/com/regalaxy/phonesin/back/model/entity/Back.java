package com.regalaxy.phonesin.back.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.regalaxy.phonesin.back.model.BackDto;
import com.regalaxy.phonesin.global.BaseTimeEntity;
import com.regalaxy.phonesin.rental.model.entity.Rental;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "back")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "back")
public class Back {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long backId;

    // 나중에 rental 코드 들어오면 다시 작성하기
    @JsonIgnore
    @OneToOne(mappedBy = "back", fetch = LAZY)
    private Rental rental;
//    private Long rentalId;

    @Column(nullable = false)
    private int backStatus; // 반납 상태 : 신청(1), 승인(2), 수거완료(3), 상태확인(4)
    private String backDeliveryDate;
    private LocalDateTime applyDate;
    private String backDeliveryLocationType; // 배달, 서비스센터 제출, 직접 제출
    private String backDeliveryLocation; // 1 배달의 경우 배달지 주소, 서비스센터 제출의 경우 서비스센터 주소
    private String backZipcode; // 주소의 우편번호
    private String review; // 사용 후기 또는 조기 반납 이유

    public static Back toBackEntity(BackDto backDto) {
        Back back = new Back();
        back.setBackStatus(backDto.getBackStatus());
        back.setBackDeliveryDate(backDto.getBackDeliveryDate());
        back.setBackDeliveryLocationType(backDto.getBackDeliveryLocationType());
        back.setBackDeliveryLocation(backDto.getBackDeliveryLocation());
        back.setBackZipcode(backDto.getBackZipcode());
        back.setReview(backDto.getReview());
        return back;
    }
}
