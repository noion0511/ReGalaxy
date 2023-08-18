package com.regalaxy.phonesin.rental.model.repository;

import com.regalaxy.phonesin.rental.model.RentalDetailDto;
import com.regalaxy.phonesin.rental.model.RentalDto;
import com.regalaxy.phonesin.rental.model.RentalSearchDto;

import java.util.List;

public interface RentalRepositoryCustom {
    List<RentalDto> search(RentalSearchDto rentalSearchDto);
    List<RentalDto> searchById(Long member_id);

    RentalDetailDto detailInfo(Long rental_id);
}
