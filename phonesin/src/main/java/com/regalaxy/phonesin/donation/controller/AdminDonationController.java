package com.regalaxy.phonesin.donation.controller;

import com.regalaxy.phonesin.donation.model.service.DonationService;
import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.rental.model.RentalDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/donation")
@Api(value = "관리자 휴대폰 기증 API", description = "관리자 휴대폰 기증 Controller")
public class AdminDonationController {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    private final DonationService DonationService;

    @ApiOperation(value = "관리자 기기 기증 신청서 상세조회")
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

    @ApiOperation(value = "관리자 기기 기증 신청서 리스트")
    @GetMapping("/list")
    public ModelAndView donationList() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("list", DonationService.donationList());
        mav.addObject("title", "기증");
        mav.setViewName("/list");//어디로 이동할지 ex) rental/list
        return mav;
    }
}
