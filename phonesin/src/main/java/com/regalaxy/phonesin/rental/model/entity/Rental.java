package com.regalaxy.phonesin.rental.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.regalaxy.phonesin.address.model.entity.Agency;
import com.regalaxy.phonesin.back.model.entity.Back;
import com.regalaxy.phonesin.donation.model.entity.Donation;
import com.regalaxy.phonesin.global.BaseTimeEntity;
import com.regalaxy.phonesin.member.model.entity.Member;
import com.regalaxy.phonesin.phone.model.entity.Phone;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
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

    @OneToMany(mappedBy = "rental", cascade = ALL, orphanRemoval = true)
    private List<Back> backList = new ArrayList<Back>();

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
    @OneToMany(mappedBy = "rental", cascade = ALL, orphanRemoval = true)
    private List<Phone> phoneList = new ArrayList<Phone>();
    public void extension(){
        this.isExtension = true;
    }
}
