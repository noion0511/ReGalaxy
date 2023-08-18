package com.regalaxy.phonesin.address.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="agency")
@Getter
@Setter
@Table(name="agency")
public class Agency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agencyId;
    @Column(name="agency_x")
    private double agencyX;
    @Column(name="agency_y")
    private double agencyY;
    private String agencyPhoneNum;
    private String agencyLocation;
    private String agencyName;
    private String agencyPhotoUrl;
}
