package com.regalaxy.phonesin.rental.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalDto {
    private Long rentalId;
    private LocalDateTime applyDate;
    private LocalDateTime rentalStart;
    private LocalDateTime rentalEnd;
    private int rentalStatus;
    private String rentalDeliveryLocation;
    private int fund;
    private String modelName;
    private Long phoneId;
    private String waybillNumber;
    private boolean isY2K;
    private boolean isClimateHumidity;
    private boolean isHomecam;
}
