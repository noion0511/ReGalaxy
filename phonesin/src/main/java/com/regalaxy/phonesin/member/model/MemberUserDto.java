package com.regalaxy.phonesin.member.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.regalaxy.phonesin.donation.model.entity.Donation;
import com.regalaxy.phonesin.member.model.entity.Member;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberUserDto {
    private String email;
    private String memberName;
    private String phoneNumber;
    private Boolean isCha;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updatedAt;

    public MemberUserDto(Member member) {
        this.email = member.getEmail();
        this.memberName = member.getMemberName();
        this.phoneNumber = member.getPhoneNumber();
        this.isCha = member.getIsCha();
        this.createdAt = member.getCreatedAt();
        this.updatedAt = member.getUpdatedAt();
    }
}
