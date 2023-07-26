package com.regalaxy.phonesin.back.model.entity;

import com.regalaxy.phonesin.back.model.BackDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Back {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "back_id")
    private Long backId;

    // 나중에 rental 코드 들어오면 다시 작성하기
//    @OneToOne(mappedBy = "back")
//    private Rental rentals = new Rental();
    @Column(name = "rental_id")
    private Long rentalId;

    @Column(nullable = false, name = "back_status")
    private int backStatus; // 반납 상태 : 신청(1), 승인(2), 수거완료(3), 상태확인(4)

    @Column(nullable = false, name = "back_delivery_date")
    private LocalDate backDeliveryDate; // 반납 배송 날짜

    @Column(nullable = false, name = "apply_date")
    private LocalDateTime applyDate; // 반납 신청 날짜 (시간까지 다 보여줌)

    @Column(name = "back_delivery_location_type")
    private String backDeliveryLocationType; // 배달, 서비스센터 제출, 직접 제출

    @Column(name = "back_delivery_location")
    private String backDeliveryLocation; // 1 배달의 경우 배달지 주소, 서비스센터 제출의 경우 서비스센터 주소

    @Column(name = "back_zipcode")
    private String backZipcode; // 주소의 우편번호
    private String review; // 사용 후기 또는 조기 반납 이유

    public static Back toBackEntity(BackDto backDto) {
        Back back = new Back();
        back.setBackId(backDto.getBackId());
        back.setRentalId(backDto.getRentalId());
        back.setBackStatus(backDto.getBackStatus());
        back.setBackDeliveryDate(backDto.getBackDeliveryDate());
        back.setApplyDate(backDto.getApplyDate());
        back.setBackDeliveryLocationType(backDto.getBackDeliveryLocationType());
        back.setBackDeliveryLocation(backDto.getBackDeliveryLocation());
        back.setBackZipcode(backDto.getBackZipcode());
        back.setReview(backDto.getReview());
        return back;
    }
}
