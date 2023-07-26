package com.regalaxy.phonesin.back.model.entity;

import com.regalaxy.phonesin.back.model.BackDto;
import com.regalaxy.phonesin.member.model.entity.Member;
import com.regalaxy.phonesin.rental.model.entity.Rental;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Back {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int back_id;

    @OneToOne(mappedBy = "back")
    private List<Rental> rentals = new ArrayList<>();

    @Column(nullable = false)
    private int back_status;

    @Column(nullable = false)
    private LocalDate back_delivery_date;

    @Column(nullable = false)
    private LocalDateTime apply_date;

    private String back_delivery_location_type;
    private String back_delivery_location;
    private String back_zipcode;
    private String review;

    public static Back toBackEntity(BackDto BackDto) {
        Back back = new Back();
        back.setBack_id(BackDto.getBack_id());
        back.setBack_status(back.getBack_status());
        back.setBack_delivery_date(back.getBack_delivery_date());
        back.setApply_date(back.getApply_date());
        back.setBack_delivery_location_type(back.getBack_delivery_location_type());
        back.setBack_zipcode(back.getBack_zipcode());
        back.setReview(back.getReview());
        return back;
    }
}
