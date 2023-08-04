package com.regalaxy.phonesin.phone.model;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDto {
    private Long phoneId;
    private String modelName;
    private String nickname;
    private Long rentalId;
}
