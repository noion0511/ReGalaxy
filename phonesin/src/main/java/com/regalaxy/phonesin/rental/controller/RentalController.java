package com.regalaxy.phonesin.rental.controller;

import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.rental.model.RentalDetailDto;
import com.regalaxy.phonesin.rental.model.RentalDto;
import com.regalaxy.phonesin.rental.model.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rental")
@CrossOrigin("*")
public class RentalController {
    @Autowired
    private RentalService rentalService;
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @PostMapping("/apply")//신청하기
    public ResponseEntity<?> infoApply(RentalDetailDto rentalDetailDto, int using_date){
        rentalService.infoApply(rentalDetailDto, using_date);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @PutMapping("/apply/update")//신청수정
    public ResponseEntity<?> infoUpdate(RentalDetailDto rentalDetailDto){
        rentalService.infoUpdated(rentalDetailDto);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/apply/delete")//신청삭제
    public ResponseEntity<?> infoDelete(Long rental_id){
        rentalService.infoDelete(rental_id);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/apply/list")//신청 리스트
    public ResponseEntity<?> infoList(SearchDto searchDto){
        List<RentalDto> list = rentalService.infoList(searchDto);
        return new ResponseEntity<List<RentalDto>>(list, HttpStatus.OK);
    }

    @PutMapping("/apply/extension")//신청 연장
    public ResponseEntity<?> extension(Long rental_id){
        boolean result = rentalService.extension(rental_id);
        if(result) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }else{
            return new ResponseEntity<String>(FAIL, HttpStatus.OK);
        }
    }
}
