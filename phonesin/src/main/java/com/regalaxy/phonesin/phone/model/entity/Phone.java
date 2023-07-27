package com.regalaxy.phonesin.phone.model.entity;

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
    private Long phone_id;
    private Long rental_id;
    @ManyToOne
    @JoinColumn(name="model_id")
    private Model model;
    @OneToOne
    @JoinColumn(name="id")
    private Donation donation;
}
