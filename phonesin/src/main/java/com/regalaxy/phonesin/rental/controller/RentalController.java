package com.regalaxy.phonesin.rental.controller;

import com.regalaxy.phonesin.rental.model.RentalApplyDto;
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
    public ResponseEntity<?> infoApply(@RequestBody RentalApplyDto rentalApplyDto){
        rentalService.infoApply(rentalApplyDto);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @ApiOperation(value = "기기 대여 신청서 수정")
    @PutMapping("/apply/update/{rental_id}")//신청수정
    public ResponseEntity<?> infoUpdate(@RequestBody RentalApplyDto rentalApplyDto, @PathVariable("rental_id") Long rental_id){
        rentalService.infoUpdated(rentalApplyDto, rental_id);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @ApiOperation(value = "기기 대여 신청서 삭제")
    @DeleteMapping("/apply/delete/{rental_id}")//신청삭제
    public ResponseEntity<?> infoDelete(@PathVariable("rental_id") Long rental_id){
        rentalService.infoDelete(rental_id);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @ApiOperation(value = "기기 대여 신청서 리스트 조회")
    @GetMapping("/apply/list/{member_id}")//신청 리스트
    public ResponseEntity<?> infoList(@PathVariable("member_id") Long member_id){
        List<RentalDto> list = rentalService.clientInfoList(member_id);
        return new ResponseEntity<List<RentalDto>>(list, HttpStatus.OK);
    }

    @ApiOperation(value = "기기 대여 신청 연장")
    @PutMapping("/apply/extension/{rental_id}")//신청 연장
    public ResponseEntity<?> extension(@PathVariable("rental_id") Long rental_id){
        boolean result = rentalService.extension(rental_id);
        if(result) {
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("이미 연장된 대여신청입니다.", HttpStatus.OK);
        }
    }

    @ApiOperation(value = "기기 대여 신청 수")
    @GetMapping("/apply/count/{member_id}")//신청 연장
    public ResponseEntity<?> count(@PathVariable("member_id") Long member_id){
        int cnt = rentalService.count(member_id);
        return new ResponseEntity<Integer>(cnt, HttpStatus.OK);
    }
}
