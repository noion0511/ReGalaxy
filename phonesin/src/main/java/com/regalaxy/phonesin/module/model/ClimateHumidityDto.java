package com.regalaxy.phonesin.module.model;

import com.regalaxy.phonesin.module.model.entity.Ytwok;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class ClimateHumidityDto {
    private Float longitude;
    private Float latitude;

    @Builder
    public ClimateHumidityDto (Float longitude, Float latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
