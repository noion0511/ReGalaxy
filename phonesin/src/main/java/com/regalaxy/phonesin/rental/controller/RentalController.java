package com.regalaxy.phonesin.rental.controller;

import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.rental.model.RentalDetailDto;
import com.regalaxy.phonesin.rental.model.RentalDto;
import com.regalaxy.phonesin.rental.model.service.RentalService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "기기 대여 신청서 신청")
    @PostMapping("/apply")//신청하기
    public ResponseEntity<?> infoApply(RentalDetailDto rentalDetailDto, int using_date){
        rentalService.infoApply(rentalDetailDto, using_date);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @ApiOperation(value = "기기 대여 신청서 수정")
    @PutMapping("/apply/update")//신청수정
    public ResponseEntity<?> infoUpdate(RentalDetailDto rentalDetailDto){
        rentalService.infoUpdated(rentalDetailDto);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @ApiOperation(value = "기기 대여 신청서 삭제")
    @DeleteMapping("/apply/delete")//신청삭제
    public ResponseEntity<?> infoDelete(Long rental_id){
        rentalService.infoDelete(rental_id);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @ApiOperation(value = "기기 대여 신청서 리스트 조회")
    @GetMapping("/apply/list")//신청 리스트
    public ResponseEntity<?> infoList(@RequestBody SearchDto searchDto){
        List<RentalDto> list = rentalService.infoList(searchDto);
        return new ResponseEntity<List<RentalDto>>(list, HttpStatus.OK);
    }

    @ApiOperation(value = "기기 대여 신청 연장")
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
