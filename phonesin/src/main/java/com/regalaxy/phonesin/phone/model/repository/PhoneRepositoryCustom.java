package com.regalaxy.phonesin.phone.model.repository;

import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.phone.model.PhoneDto;

import java.util.List;

public interface PhoneRepositoryCustom {
    List<PhoneDto> search(SearchDto searchDto);
}
