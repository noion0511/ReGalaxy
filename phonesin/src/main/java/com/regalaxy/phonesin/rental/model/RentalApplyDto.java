package com.regalaxy.phonesin.rental.model;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalApplyDto {
    private boolean isY2K;//rental
    private boolean isClimateHumidity;//rental
    private boolean isHomecam;//rental
    private int count;//rental
    private String rentalDeliveryLocation;//rental
    private int fund;//rental
    private int usingDate;
}
