package com.regalaxy.phonesin.address.controller;

import com.regalaxy.phonesin.address.model.AddressDto;
import com.regalaxy.phonesin.address.model.AgencyDto;
import com.regalaxy.phonesin.address.model.LocationDto;
import com.regalaxy.phonesin.address.model.entity.Address;
import com.regalaxy.phonesin.address.model.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
@CrossOrigin("*")
public class AddressController {
    @Autowired
    private AddressService addressService;
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @GetMapping("/list")//주소 목록
    public ResponseEntity<?> addressList(Long member_id){
        List<AddressDto> list = addressService.addressList(member_id);
        return new ResponseEntity<List<AddressDto>>(list, HttpStatus.OK);
    }

    @GetMapping("/list/samsung")//삼성 대리점 목록//현재 위치
    public ResponseEntity<?> samsungList(LocationDto locationDto){
        List<AgencyDto> list = addressService.samsungList(locationDto);
        return new ResponseEntity<List<AgencyDto>>(list, HttpStatus.OK);
    }

    @GetMapping("/list/samsung/search")//삼성 대리점 목록//검색
    public ResponseEntity<?> samsungListSearsch(LocationDto locationDto){
        List<AgencyDto> list = addressService.samsungListSearch(locationDto);
        return new ResponseEntity<List<AgencyDto>>(list, HttpStatus.OK);
    }

    @DeleteMapping("/delete")//삼성 대리점 목록//검색
    public ResponseEntity<?> delete(Long address_id){
        addressService.delete(address_id);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @PostMapping("/create")//삼성 대리점 목록//검색
    public ResponseEntity<?> create(String address, Long member_id){
        addressService.create(address, member_id);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }
}
