package com.regalaxy.phonesin.rental.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalDto {
    private int rental_id;
    private String rental_start;
    private String rental_end;
    private int rental_status;
    private String rental_delivery_location;
    private int fund;
    private String model_name;
    private int phone_id;
    private String waybill_number;
}
