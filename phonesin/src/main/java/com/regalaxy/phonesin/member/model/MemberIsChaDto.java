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
    private DmSearch dmSearch;
    private DmScr dmScr;

    @Getter
    @Setter
    public static class DmSearch {
        private String certfIssuDcd;
        private String aplyRelpsFlnm;
        private String aplyRelpsIdmbIdnfNo;
        private String certfIssuNo;
        private String aplyRelpsRrnoBrdt;
    }

    @Getter
    @Setter
    public static class DmScr {
        private String curScrId;
        private String befScrId;
    }
}
