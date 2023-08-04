package com.regalaxy.phonesin.rental.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.regalaxy.phonesin.address.model.entity.Agency;
import com.regalaxy.phonesin.back.model.entity.Back;
import com.regalaxy.phonesin.global.BaseTimeEntity;
import com.regalaxy.phonesin.member.model.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "rental")
@Getter
@Setter
@Table(name = "rental")
public class Rental extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentalId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "agency_id")
    private Agency agency;

    private LocalDateTime applyDate;

    private LocalDateTime rentalStart;
    private LocalDateTime rentalEnd;
    private int rentalStatus;
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
    private String rentalDeliveryLocation;
    private int rentalZipcode;
    private String waybillNumber;
    private int fund;
    private int usingDate;
    public void extension(){
        this.isExtension = true;
    }
}
