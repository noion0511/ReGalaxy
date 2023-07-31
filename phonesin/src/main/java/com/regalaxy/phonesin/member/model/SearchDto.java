package com.regalaxy.phonesin.member.model;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {
    private int pgno;//1 2 3
    private String email;// gmlwncl@
    private int isBlack;//1: 전체, 2:블랙인 사람, 3:블랙이 아닌 사람
    private int isCha;//1: 전체, 2:블랙인 사람, 3:블랙이 아닌 사람

    private int isRental;
    private String model_name;
    private int isY2K;
    private int isClimateHumidity;
    private int isHomecam;
}
