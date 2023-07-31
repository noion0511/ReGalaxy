package com.regalaxy.phonesin.rental.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.regalaxy.phonesin.address.model.entity.Agency;
import com.regalaxy.phonesin.back.model.entity.Back;
import com.regalaxy.phonesin.member.model.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "rental")
@Getter
@Setter
@Table(name = "rental")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rental_id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "agency_id")
    private Agency agency;

    private LocalDateTime apply_date;

    private LocalDateTime rental_start;
    private LocalDateTime rental_end;
    private int rental_status;
    @Column(name="isy2k")
    private boolean isY2K;
    @Column(name="is_climate_humidity")
    private boolean isClimateHumidity;
    @Column(name="is_homecam")
    private boolean isHomecam;
    private int count;
    @Column(name="is_extension")
    private boolean isExtension;
    @Column(name = "rental_delivery_location")
    private String rental_deliverylocation;
    private int rental_zipcode;
    private String waybill_number;
    private int fund;
    private int using_date;
    public void extension(){
        this.isExtension = true;
    }
}
