package com.regalaxy.phonesin.address.model.service;

import com.regalaxy.phonesin.address.model.AddressDto;
import com.regalaxy.phonesin.address.model.AgencyDto;
import com.regalaxy.phonesin.address.model.LocationDto;
import com.regalaxy.phonesin.address.model.entity.Address;
import com.regalaxy.phonesin.address.model.entity.Agency;
import com.regalaxy.phonesin.address.model.repository.AddressRepository;
import com.regalaxy.phonesin.address.model.repository.AgencyRepository;
import com.regalaxy.phonesin.member.model.entity.Member;
import com.regalaxy.phonesin.member.model.repository.MemberRepository;
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
    @Autowired
    private MemberRepository memberRepository;

    public List<AddressDto> addressList(Long member_id){
        return addressRepository.addressList(member_id);
    }

    public List<AgencyDto> samsungList(LocationDto locationDto){
        List<AgencyDto> list = agencyRepository.samsungList(locationDto);
        int num=0;
        for(AgencyDto agencyDto: list){
            double d = distance(agencyDto.getAgencyY(), agencyDto.getAgencyX(), locationDto.getLatitude(), locationDto.getLongitude());
            list.get(num++).setDistance(d*1000);
        }
        return list;
    }

    public List<AgencyDto> samsungListSearch(LocationDto locationDto){
        List<AgencyDto> list = agencyRepository.samsungListSearch(locationDto);
        int num=0;
        for(AgencyDto agencyDto: list){
            double d = distance(agencyDto.getAgencyY(), agencyDto.getAgencyX(), locationDto.getLatitude(), locationDto.getLongitude());
            list.get(num++).setDistance(d*1000);
        }
        return list;
    }

    public void delete(Long address_id){
        addressRepository.deleteById(address_id);
    }

    public void create(String address, Long member_id){
        Address addressEntity = new Address();
        addressEntity.setAddress(address);
        Member member = memberRepository.findById(member_id).get();
        addressEntity.setMember(member);
        addressRepository.save(addressEntity);
    }

    public double distance(double lat1, double lon1, double lat2, double lon2) {
        int R = 6371; // 지구 반지름 (단위: km)
        double dLat = deg2rad(lat2 - lat1);
        double dLon = deg2rad(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = R * c; // 두 지점 간의 거리 (단위: km)
        return distance;
    }

    public double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }
}
