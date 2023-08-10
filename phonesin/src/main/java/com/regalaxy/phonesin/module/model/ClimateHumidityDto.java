package com.regalaxy.phonesin.module.model;

import com.regalaxy.phonesin.module.model.entity.Ytwok;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class ClimateHumidityDto {
    private Float logitude;
    private Float latitude;

    @Builder
    public ClimateHumidityDto (Float logitude, Float latitude){
        this.logitude = logitude;
        this.latitude = latitude;
    }
}
