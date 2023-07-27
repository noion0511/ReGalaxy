package com.regalaxy.phonesin.member.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private Long member_id;
    private String email;
    private String member_name;
    private String password;
    private String phone_number;
    private Boolean isCha;
    private Boolean isBlackList;
    private Boolean isDelete;
    private Boolean isManager;
}
