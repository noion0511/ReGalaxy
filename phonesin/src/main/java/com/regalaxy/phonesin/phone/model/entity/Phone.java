package com.regalaxy.phonesin.phone.model.entity;

import com.regalaxy.phonesin.back.model.BackDto;
import com.regalaxy.phonesin.back.model.entity.Back;
import com.regalaxy.phonesin.donation.model.entity.Donation;
import com.regalaxy.phonesin.member.model.entity.Member;
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
    private Long rentalId;
    @OneToOne(mappedBy = "phone", cascade = CascadeType.ALL)
    private Back back;
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
