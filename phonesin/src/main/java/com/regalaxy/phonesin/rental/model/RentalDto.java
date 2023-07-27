package com.regalaxy.phonesin.rental.model;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalDto {
    private Long rental_id;
    private String rental_start;
    private String rental_end;
    private int rental_status;
    private String rental_deliverylocation;
    private int fund;
    private String model_name;
    private Long phone_id;
    private String waybill_number;
}
