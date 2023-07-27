package com.regalaxy.phonesin.donation.controller;
import com.regalaxy.phonesin.donation.model.dto.DonationDto;
import com.regalaxy.phonesin.donation.model.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/donation")
@Api(value = "휴대폰 기증 API", description = "휴대폰 기증 Controller")
public class DonationController {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    private final DonationService DonationService;

    @ApiOperation(value = "기기 기부 신청서 상세조회")
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> donationInfo(@RequestBody Long donation_id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        System.out.println(donation_id);
        try {
            resultMap.put("donation", DonationService.donationInfo(donation_id));
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            resultMap.put("message", FAIL);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "기기 기부 신청서 리스트")
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> donationList() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap.put("donation", DonationService.donationList());
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            resultMap.put("message", FAIL);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "기기 기부 신청서 작성")
    @PostMapping("/apply")
    public ResponseEntity<Map<String, Object>> donationApply(@RequestBody DonationDto donationDto) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            if (!DonationService.donationApply(donationDto)) throw new RuntimeException();
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.toString());
            resultMap.put("message", FAIL);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }
    }


    @ApiOperation(value = "기기 기부 신청서 수정")
    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> donationUpdate(@RequestBody DonationDto donationDto) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap.put("donation", DonationService.donationUpdate(donationDto));
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            resultMap.put("message", FAIL);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "기기 기부 신청서 삭제")
    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, Object>> donationDelete(@RequestBody Long donation_id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap.put("donation", DonationService.donationDelete(donation_id));
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            resultMap.put("message", FAIL);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "이달의 기부왕")
    @GetMapping("/king")
    public ResponseEntity<Map<String, Object>> donationKing() {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap.put("donation", DonationService.donationKing());
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            resultMap.put("message", FAIL);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }
    }
}
