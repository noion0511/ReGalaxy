package com.regalaxy.phonesin.rental.model.repository;

import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.rental.model.ApplyDto;
import com.regalaxy.phonesin.rental.model.RentalDetailDto;
import com.regalaxy.phonesin.rental.model.RentalDto;

import java.util.List;

public interface RentalRepositoryCustom {
    List<RentalDto> search(SearchDto searchDto);
    boolean extension(Long rental_id);
    boolean apply(ApplyDto applyDto);

    RentalDetailDto detailInfo(Long rental_id);
}
