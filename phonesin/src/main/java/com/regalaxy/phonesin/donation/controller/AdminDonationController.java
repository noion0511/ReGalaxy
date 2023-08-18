package com.regalaxy.phonesin.donation.controller;

import com.regalaxy.phonesin.donation.model.DonationResponseDto;
import com.regalaxy.phonesin.donation.model.DonationSearchDto;
import com.regalaxy.phonesin.donation.model.service.DonationService;
import com.regalaxy.phonesin.phone.model.PhoneDto;
import com.regalaxy.phonesin.phone.model.PhoneSearchDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        System.out.println(DonationService.donationList().toString());
        mav.addObject("title", "기증");
        mav.setViewName("list");//어디로 이동할지 ex) rental/list
        return mav;
    }

    @ApiOperation(value = "관리자 기기 기증 신청서 리스트 검색")
    @PostMapping("/list")
    public ResponseEntity<?> donationSearchList(@RequestBody DonationSearchDto donationSearchDto, Model model) throws Exception {
        Map<String, Object> map = new HashMap<>();
        System.out.println(donationSearchDto.isAccept());
        System.out.println(donationSearchDto.isReceive());
        List<DonationResponseDto> list = DonationService.donationSearchList(donationSearchDto);
        model.addAttribute("list", list);
        model.addAttribute("title", "기증");
        map.put("list", list);
        map.put("title", "기증");
        return ResponseEntity.ok(map);
    }

    @ApiOperation(value = "관리자 기기 기증 수락")
    @PutMapping("/accept/{donationId}")
    public ResponseEntity<?> accept(@PathVariable("donationId") Long donationId, Model model) throws Exception{
        DonationService.adminDonationApply(donationId, 2);
        Map<String, Object> map = new HashMap<>();
        model.addAttribute("list", DonationService.donationList());
        model.addAttribute("title", "휴대폰");
        map.put("list", DonationService.donationList());
        map.put("title", "휴대폰");
        return ResponseEntity.ok(map);
    }

    @ApiOperation(value = "관리자 기기 기증 도착 완료")
    @PutMapping("/receive/{donationId}")
    public ResponseEntity<?> receive(@PathVariable("donationId") Long donationId, Model model) throws Exception{
        DonationService.adminDonationApply(donationId, 3);
        Map<String, Object> map = new HashMap<>();
        model.addAttribute("list", DonationService.donationList());
        model.addAttribute("title", "휴대폰");
        map.put("list", DonationService.donationList());
        map.put("title", "휴대폰");
        return ResponseEntity.ok(map);
    }
}
