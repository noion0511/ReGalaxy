package com.regalaxy.phonesin.rental.model.repository;

import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.rental.model.ApplyDto;
import com.regalaxy.phonesin.rental.model.RentalDetailDto;

import java.util.List;

public interface RentalRepositoryCustom {
    List<RentalDetailDto> search(SearchDto searchDto);
    boolean extension(int rental_id);
    boolean apply(ApplyDto applyDto);
}
