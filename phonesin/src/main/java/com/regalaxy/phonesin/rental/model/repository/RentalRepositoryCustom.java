package com.regalaxy.phonesin.rental.model.repository;

import com.regalaxy.phonesin.member.model.SearchDto;

public interface RentalRepositoryCustom {
    String search(SearchDto searchDto);
}
