package com.regalaxy.phonesin.phone.model;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneUpdateDto {
    private Long phoneId;
    private Long donationId;
    private String serialNumber;
    private Long modelId;
    private boolean isY2K;
    private boolean isClimateHumidity;
    private boolean isHomecam;

    private boolean searschRental;
    private boolean searschY2K;
    private boolean searschClimateHumidity;
    private boolean searschHomecam;
}
