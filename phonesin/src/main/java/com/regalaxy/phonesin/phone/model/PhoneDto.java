package com.regalaxy.phonesin.phone.model;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDto {
    private Long phoneId;
    private String serialNumber;
    private String modelName;
    private String nickname;
    private boolean isRental;
    private boolean isY2K;
    private boolean isClimateHumidity;
    private boolean isHomecam;
}
