package com.regalaxy.phonesin.rental.controller;

import com.regalaxy.phonesin.member.model.jwt.JwtTokenProvider;
import com.regalaxy.phonesin.rental.model.RentalApplyDto;
import com.regalaxy.phonesin.rental.model.RentalDto;
import com.regalaxy.phonesin.rental.model.service.RentalService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rental")
@CrossOrigin("*")
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @ApiOperation(value = "기기 대여 신청서 신청")
    @PostMapping("/apply")//신청하기
    public ResponseEntity<?> infoApply(@RequestBody List<RentalApplyDto> rentalApplylistDto, @ApiIgnore @RequestHeader String authorization){
        String token = authorization.replace("Bearer ", "");
        Long memberId = jwtTokenProvider.getMemberId(token);
        for (RentalApplyDto rentalApplyDto : rentalApplylistDto) {
            rentalService.infoApply(rentalApplyDto, memberId);
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("message", SUCCESS);
        resultMap.put("status", 200);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "기기 대여 신청서 수정")
    @PutMapping("/apply/update/{rental_id}")//신청수정
    public ResponseEntity<?> infoUpdate(@RequestBody RentalApplyDto rentalApplyDto, @PathVariable("rental_id") Long rental_id){
        rentalService.infoUpdated(rentalApplyDto, rental_id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("message", SUCCESS);
        resultMap.put("status", 200);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "기기 대여 신청서 삭제")
    @DeleteMapping("/apply/delete/{rental_id}")//신청삭제
    public ResponseEntity<?> infoDelete(@PathVariable("rental_id") Long rental_id){
        rentalService.infoDelete(rental_id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("message", SUCCESS);
        resultMap.put("status", 200);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "기기 대여 신청서 리스트 조회")
    @GetMapping("/apply/list")//신청 리스트
    public ResponseEntity<?> infoList(@ApiIgnore @RequestHeader String authorization){
        String token = authorization.replace("Bearer ", "");
        Long memberId = jwtTokenProvider.getMemberId(token);
        List<RentalDto> list = rentalService.clientInfoList(memberId);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("rental list", list);
        resultMap.put("message", SUCCESS);
        resultMap.put("status", 200);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "기기 대여 신청 연장")
    @PutMapping("/apply/extension/{rental_id}")//신청 연장
    public ResponseEntity<?> extension(@PathVariable("rental_id") Long rental_id){
        boolean result = rentalService.extension(rental_id);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        if(result) {
            resultMap.put("message", SUCCESS);
            resultMap.put("status", 200);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }else{
            resultMap.put("message", "이미 연장된 대여신청입니다.");
            resultMap.put("status", 400);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "기기 대여 신청 수")
    @GetMapping("/apply/count")//신청 연장
    public ResponseEntity<?> count(@ApiIgnore @RequestHeader String authorization){
        String token = authorization.replace("Bearer ", "");
        Long memberId = jwtTokenProvider.getMemberId(token);
        int cnt = rentalService.count(memberId);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("rental count", cnt);
        resultMap.put("status", 200);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "총 기부액")
    @GetMapping("/total/fund")
    public ResponseEntity<?> totalFund(){
        Double sumFund = rentalService.sumFund();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("Total Fund", sumFund);
        resultMap.put("message", "성공적으로 조회하였습니다.");
        resultMap.put("status", 200);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
}
