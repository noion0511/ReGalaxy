package com.regalaxy.phonesin.member.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private Long memberId;
    private String email;
    private String memberName;
    private String password;
    private String phoneNumber;
    private Boolean isCha;
    private Boolean isBlackList;
    private Boolean isDelete;
    private Boolean isManager;
}