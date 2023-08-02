package com.regalaxy.phonesin.address.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private double latitude;
    private double longitude;
    private String search;
}
