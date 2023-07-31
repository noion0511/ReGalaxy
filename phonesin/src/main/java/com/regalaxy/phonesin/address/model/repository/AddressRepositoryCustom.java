package com.regalaxy.phonesin.address.model.repository;

import com.regalaxy.phonesin.address.model.AddressDto;
import com.regalaxy.phonesin.address.model.AgencyDto;
import com.regalaxy.phonesin.address.model.LocationDto;

import java.util.List;

public interface AddressRepositoryCustom {
    List<AddressDto> addressList(Long member_id);
    List<AgencyDto> samsungList(LocationDto locationDto);
    List<AgencyDto> samsungListSearch(LocationDto locationDto);
}
