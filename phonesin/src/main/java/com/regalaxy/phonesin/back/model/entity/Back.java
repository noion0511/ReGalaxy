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
    private Long back_id;

    // 나중에 rental 코드 들어오면 다시 작성하기
//    @OneToOne(mappedBy = "back")
//    private Rental rentals = new Rental();
    private Long rental_id;

    @Column(nullable = false)
    private int back_status; // 반납 상태 : 신청(1), 승인(2), 수거완료(3), 상태확인(4)

    @Column(nullable = false)
    private LocalDate back_delivery_date; // 반납 배송 날짜

    @Column(nullable = false)
    private LocalDateTime apply_date; // 반납 신청 날짜 (시간까지 다 보여줌)

    private String back_delivery_location_type; // 배달, 서비스센터 제출, 직접 제출
    private String back_delivery_location; // 1 배달의 경우 배달지 주소, 서비스센터 제출의 경우 서비스센터 주소
    private String back_zipcode; // 주소의 우편번호
    private String review; // 사용 후기 또는 조기 반납 이유

    public static Back toBackEntity(BackDto BackDto) {
        Back back = new Back();
        back.setBack_status(back.getBack_status());
        back.setBack_delivery_date(back.getBack_delivery_date());
        back.setApply_date(back.getApply_date());
        back.setBack_delivery_location_type(back.getBack_delivery_location_type());
        back.setBack_zipcode(back.getBack_zipcode());
        back.setReview(back.getReview());
        return back;
    }
}
