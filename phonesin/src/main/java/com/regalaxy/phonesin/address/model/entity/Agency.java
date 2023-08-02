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
    private double agency_x;
    private double agency_y;
    private String agencyPhoneNum;
    private String agencyLocation;
    private String agencyName;
    private String agencyPhotoUrl;
}
