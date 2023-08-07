package com.regalaxy.phonesin.phone.model.repository;

import com.regalaxy.phonesin.phone.model.PhoneDto;
import com.regalaxy.phonesin.phone.model.PhoneSearchDto;

import java.util.List;

public interface PhoneRepositoryCustom {
    List<PhoneDto> search(PhoneSearchDto phoneSearchDto);
}
