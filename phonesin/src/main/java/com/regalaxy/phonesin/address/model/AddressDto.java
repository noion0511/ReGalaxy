package com.regalaxy.phonesin.address.model;

import com.regalaxy.phonesin.member.model.entity.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private String address;
}
