package com.regalaxy.phonesin.rental.model;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRentalApplyDto {
    private Long rentalId;
    private boolean apply;
    private boolean ready;
}
