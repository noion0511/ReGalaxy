package com.regalaxy.phonesin.rental.model.repository;

import com.regalaxy.phonesin.member.model.SearchDto;

public class RentalRepositoryCustomImpl implements RentalRepositoryCustom {
    @Override
    public String search(SearchDto searchDto) {
        String query = "select * from rental where";
        return query;
    }
}
