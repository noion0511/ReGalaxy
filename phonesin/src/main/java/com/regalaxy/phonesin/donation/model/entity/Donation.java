package com.regalaxy.phonesin.donation.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int donation_id;

    private int member_id;

    private int donation_status;

    private String donation_created_at;

    private String donation_delivery_date;

    private String donation_delivery_location_type;

    private String donation_delivery_location;

    @Builder
    public Donation(int donation_status, String donation_delivery_date, String donation_delivery_location_type, String donation_delivery_location) {
        this.donation_status = donation_status;
        this.donation_delivery_date = donation_delivery_date;
        this.donation_delivery_location_type = donation_delivery_location_type;
        this.donation_delivery_location = donation_delivery_location;
    }
}
