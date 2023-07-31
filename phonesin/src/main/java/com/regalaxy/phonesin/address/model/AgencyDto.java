package com.regalaxy.phonesin.address.model;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgencyDto {
    private String agency_phone_num;
    private String agency_location;
    private String agency_name;
    private String agency_photo_url;
}
