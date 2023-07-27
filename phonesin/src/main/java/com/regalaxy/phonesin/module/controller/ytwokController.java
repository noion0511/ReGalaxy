package com.regalaxy.phonesin.module.controller;

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
@RequestMapping("/ytwok")
@Api(value = "y2k API", description = "y2k Controller")
public class ytwokController {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @ApiOperation(value = "기기 기부 신청서 상세조회")
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> donationInfo(@RequestBody Long donation_id) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        System.out.println(donation_id);
        try {
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            resultMap.put("message", FAIL);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }
    }
}
