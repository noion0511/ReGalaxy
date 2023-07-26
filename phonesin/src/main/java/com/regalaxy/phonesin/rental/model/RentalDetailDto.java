package com.regalaxy.phonesin.rental.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalDetailDto {
    private int id;
    private int rental_id;
    private int using_date;
    private int module;
    private int count;
    private String rental_delivery_location;
}
