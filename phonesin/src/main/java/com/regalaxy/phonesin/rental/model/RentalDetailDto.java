package com.regalaxy.phonesin.rental.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalDetailDto {
    private int member_id;//사용자 id//member
    private int rental_id;
    private int using_date;//며칠 대여했나/ 1개월, 2개월...
    private int module;
    private int count;
    private String rental_delivery_location;
}
