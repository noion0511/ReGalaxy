package com.regalaxy.phonesin.phone.model;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneApplyDto {
    private Long donation_id;
    private Long model_id;
    private boolean isY2K;
    private boolean isClimateHumidity;
    private boolean isHomecam;
}
