package com.regalaxy.phonesin.address.model.service;

import com.regalaxy.phonesin.address.model.AddressDto;
import com.regalaxy.phonesin.address.model.AgencyDto;
import com.regalaxy.phonesin.address.model.LocationDto;
import com.regalaxy.phonesin.address.model.entity.Address;
import com.regalaxy.phonesin.address.model.entity.Agency;
import com.regalaxy.phonesin.address.model.repository.AddressRepository;
import com.regalaxy.phonesin.address.model.repository.AgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AgencyRepository agencyRepository;

    public List<AddressDto> addressList(Long member_id){
        return addressRepository.addressList(member_id);
    }

    public List<AgencyDto> samsungList(LocationDto locationDto){
        List<AgencyDto> list = agencyRepository.samsungList(locationDto);
        return list;
    }

    public List<AgencyDto> samsungListSearch(LocationDto locationDto){
        List<AgencyDto> list = agencyRepository.samsungListSearch(locationDto);
        return list;
    }
}
