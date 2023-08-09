package com.regalaxy.phonesin.address.model;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgencyDto {
    private String agencyPhoneNum;
    private String agencyLocation;
    private String agencyName;
    private String agencyPhotoUrl;
    private double agencyX;
    private double agencyY;
    private double distance;
}
