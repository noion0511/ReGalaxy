package com.regalaxy.phonesin.donation.controller;

import com.regalaxy.phonesin.donation.model.DonationDto;
import com.regalaxy.phonesin.donation.model.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/donation")
@Api(value="휴대폰 기증 API", description = "휴대폰 기증 Controller")
public class DonationController {

    private final Logger logger = LoggerFactory.getLogger(DonationController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    @Autowired
    private DonationService DonationService;

    @ApiOperation(value = "기기 기부 신청서 작성")
    @GetMapping("/apply")
    public ResponseEntity<Map<String, Object>> donationApply(DonationDto donationDto){
        logger.info("기기 기부 신청서 작성");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            if (!DonationService.donationApply(donationDto)) throw new RuntimeException();
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            resultMap.put("message", FAIL);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }
    }
}
