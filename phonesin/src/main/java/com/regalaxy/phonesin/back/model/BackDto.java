package com.regalaxy.phonesin.back.model;

import com.regalaxy.phonesin.back.model.entity.Back;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@ToString
public class BackDto {
    private int back_status;
    private LocalDate back_delivery_date;
    private LocalDateTime apply_date;
    private String back_delivery_location_type;
    private String back_delivery_location;

    public static BackDto toBackDto(Back back) {
        BackDto backDto = new BackDto();
        backDto.setBack_status(back.getBack_status());
        backDto.setBack_delivery_date(back.getBack_delivery_date());
        backDto.setApply_date(back.getApply_date());
        backDto.setBack_delivery_location_type(back.getBack_delivery_location_type());
        backDto.setBack_delivery_location(back.getBack_delivery_location());

        return backDto;
    }
}
