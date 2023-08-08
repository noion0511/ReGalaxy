package com.regalaxy.phonesin.member.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberIsChaDto {
    private String memberName;
    private String birth;
    private String chaCode;
}
