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
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Entity(name="phone")
@Getter
@Setter
@Table(name="phone")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long phoneId;
    @OneToMany(mappedBy = "phone", cascade = ALL, orphanRemoval = true)
    private List<Rental> rentalList = new ArrayList<Rental>();
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
