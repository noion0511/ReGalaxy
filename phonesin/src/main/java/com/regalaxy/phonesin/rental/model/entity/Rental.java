package com.regalaxy.phonesin.rental.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="rental")
@Getter
@Setter
@Table(name="rental")
public class Rental {
    @Id
    private int rental_id;
    private int member_id;
    private int agency_id;
    private String apply_date;
    private String rental_start;
    private String rental_end;
    private int rental_status;
    private boolean isY2K;
    private boolean isClimateHumidity;
    private boolean isHomecam;
    private int count;
    private boolean isExtension;
    private boolean rental_delivery_location;
    private int rental_zipcode;
    private String waybill_number;
    private int fund;
}
