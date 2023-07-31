package com.regalaxy.phonesin.rental.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalDto {
    private Long rental_id;
    private LocalDateTime rental_start;
    private LocalDateTime rental_end;
    private int rental_status;
    private String rental_deliverylocation;
    private int fund;
    private String model_name;
    private Long phone_id;
    private String waybill_number;
}
