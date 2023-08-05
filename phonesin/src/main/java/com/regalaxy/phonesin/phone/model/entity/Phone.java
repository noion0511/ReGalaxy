package com.regalaxy.phonesin.phone.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.regalaxy.phonesin.back.model.BackDto;
import com.regalaxy.phonesin.back.model.entity.Back;
import com.regalaxy.phonesin.donation.model.entity.Donation;
import com.regalaxy.phonesin.member.model.entity.Member;
import com.regalaxy.phonesin.rental.model.entity.Rental;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="phone")
@Getter
@Setter
@Table(name="phone")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long phoneId;
    @OneToOne
    @JoinColumn(name = "rental_id")
    private Rental rental;
    @ManyToOne
    @JoinColumn(name="model_id")
    private Model model;
    @OneToOne
    @JoinColumn(name="donation_id")
    private Donation donation;
    private String serialNumber;
    private boolean isY2K;
    private boolean isClimateHumidity;
    private boolean isHomecam;
}
