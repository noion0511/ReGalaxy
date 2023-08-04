package com.regalaxy.phonesin.donation.controller;

import com.regalaxy.phonesin.donation.model.DonationRequestDto;
import com.regalaxy.phonesin.donation.model.service.DonationService;
import com.regalaxy.phonesin.member.model.entity.Member;
import com.regalaxy.phonesin.member.model.jwt.JwtTokenProvider;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final JwtTokenProvider jwtTokenProvider;
    private final DonationService donationService;

    @ApiOperation(value = "기기 기증 신청서 상세 조회")
    @GetMapping("/info/{donationId}")
    public ResponseEntity<Map<String, Object>> donationInfo(@PathVariable Long donationId) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("donation", donationService.donationInfo(donationId));
        resultMap.put("status", 200);
        resultMap.put("message", SUCCESS);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "기기 기증 신청서 리스트 조회")
    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> donationList() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("donation", donationService.donationList());
        resultMap.put("status", 200);
        resultMap.put("message", SUCCESS);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

    }

    @ApiOperation(value = "기기 기증 신청서 신청")
    @PostMapping("/apply")
    public ResponseEntity<Map<String, Object>> donationApply(@RequestBody DonationRequestDto donationRequestDto, @RequestHeader String authorization) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Long memberId = jwtTokenProvider.getMemberId(authorization.split(" ")[1]);
        donationService.donationApply(donationRequestDto, memberId);
        resultMap.put("status", 201);
        resultMap.put("message", SUCCESS);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.CREATED);
    }


    @ApiOperation(value = "기기 기증 신청서 수정")
    @PutMapping("/update/{donationId}")
    public ResponseEntity<Map<String, Object>> donationUpdate(@RequestBody DonationRequestDto donationRequestDto, @PathVariable Long donationId) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        donationService.donationUpdate(donationRequestDto, donationId);
        resultMap.put("status", 200);
        resultMap.put("message", SUCCESS);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "기기 기증 신청서 삭제")
    @DeleteMapping("/delete/{donationId}")
    public ResponseEntity<Map<String, Object>> donationDelete(@PathVariable Long donationId) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        donationService.donationDelete(donationId);
        resultMap.put("status", 200);
        resultMap.put("message", SUCCESS);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "이달의 기증왕")
    @GetMapping("/king")
    public ResponseEntity<Map<String, Object>> donationKing() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("donation", donationService.donationKing());
        resultMap.put("status", 200);
        resultMap.put("message", SUCCESS);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "특정 멤버의 기증 리스트")
    @GetMapping("/member/")
    public ResponseEntity<Map<String, Object>> donationList(@RequestHeader String authorization) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Long memberId = jwtTokenProvider.getMemberId(authorization.split(" ")[1]);
        resultMap.put("donation", donationService.donationlist(memberId));
        resultMap.put("status", 200);
        resultMap.put("message", SUCCESS);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
}
