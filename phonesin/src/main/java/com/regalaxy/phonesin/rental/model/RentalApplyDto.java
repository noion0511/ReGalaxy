package com.regalaxy.phonesin.rental.model;

import java.time.LocalDateTime;

public class RentalApplyDto {
    private Long memberId;
    private boolean isY2K;//rental
    private boolean isClimateHumidity;//rental
    private boolean isHomecam;//rental
    private int count;//rental
    private String rentalDeliveryLocation;//rental
    private int fund;//rental
    private Long phoneId;//phone
    private Long donationId;//phone
    private int usingDate;
}
