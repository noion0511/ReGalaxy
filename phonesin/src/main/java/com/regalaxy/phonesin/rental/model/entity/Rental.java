package com.regalaxy.phonesin.rental.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.regalaxy.phonesin.address.model.entity.Agency;
import com.regalaxy.phonesin.back.model.entity.Back;
import com.regalaxy.phonesin.member.model.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    private String apply_date;
    private String rental_start;
    private String rental_end;
    private int rental_status;
    private boolean isY2K;
    private boolean isClimateHumidity;
    private boolean isHomecam;
    private int count;
    private boolean isExtension;
    @Column(name = "rental_delivery_location")
    private String rental_deliverylocation;
    private int rental_zipcode;
    private String waybill_number;
    private int fund;

    @JsonIgnore
    @OneToOne(mappedBy = "rental",fetch = LAZY, cascade = CascadeType.ALL)
    private Back back;
}
