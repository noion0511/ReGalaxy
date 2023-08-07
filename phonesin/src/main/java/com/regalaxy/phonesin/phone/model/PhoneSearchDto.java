package com.regalaxy.phonesin.phone.model;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneSearchDto {
    private boolean isRental;
    private boolean isY2K;
    private boolean isClimateHumidity;
    private boolean isHomecam;
}
