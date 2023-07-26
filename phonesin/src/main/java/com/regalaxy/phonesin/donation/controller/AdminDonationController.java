package com.regalaxy.phonesin.donation.controller;

import com.regalaxy.phonesin.donation.model.service.DonationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/donation")
@Api(value = "휴대폰 기증 관리자 API", description = "휴대폰 기증 관리자 Controller")
public class AdminDonationController {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    private final DonationService DonationService;

    @ApiOperation(value = "기기 기부 신청서 상세조회")
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> donationInfo(@RequestBody Long donation_id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
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
}
