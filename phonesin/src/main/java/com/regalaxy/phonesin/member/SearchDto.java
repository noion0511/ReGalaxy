package com.regalaxy.phonesin.member;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {
    private String email;
    private int isBlack;
    private int isCha;
}
