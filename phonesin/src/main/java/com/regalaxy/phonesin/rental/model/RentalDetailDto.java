package com.regalaxy.phonesin.rental.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalDetailDto {
//    private Member member;//rental
    private Long memberId;
    private Long rentalId;//rental
    private boolean isY2K;//rental
    private boolean isClimateHumidity;//rental
    private boolean isHomecam;//rental
    private int count;//rental
    private LocalDateTime rentalStart;//rental
    private LocalDateTime rentalEnd;//rental
    private LocalDateTime applyDate;//rental
    private int rentalStatus;//rental
    private String rentalDeliveryLocation;//rental
    private int fund;//rental
    private String modelName;
    private Long phoneId;//phone
    private Long donationId;//phone
    private int usingDate;
//    private String waybill_numbers;//rental
}
