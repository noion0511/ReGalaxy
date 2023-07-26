package com.regalaxy.phonesin.rental.model.repository;

import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.rental.model.ApplyDto;
import com.regalaxy.phonesin.rental.model.RentalDetailDto;

import java.util.List;

public class RentalRepositoryCustomImpl implements RentalRepositoryCustom {
    @Override
    public List<RentalDetailDto> search(SearchDto searchDto) {
        return null;
    }

    @Override
    public boolean extension(int rental_id) {
        return false;
    }

    @Override
    public boolean apply(ApplyDto applyDto) {
        return false;
    }
}
