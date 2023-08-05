package com.regalaxy.phonesin.rental.model;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalSearchDto {
    private boolean isBack;
    private boolean isApply;
    private boolean notApply;
    private boolean isExtension;
}
