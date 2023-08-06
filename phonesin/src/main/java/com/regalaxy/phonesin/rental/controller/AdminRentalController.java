package com.regalaxy.phonesin.rental.controller;

import com.regalaxy.phonesin.rental.model.RentalDto;
import com.regalaxy.phonesin.rental.model.RentalSearchDto;
import com.regalaxy.phonesin.rental.model.service.RentalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/rental")
@CrossOrigin("*")
@Api(value = "관리자 휴대폰 대여 API", description = "관리자 휴대폰 대여 Controller")
public class AdminRentalController {

    @Autowired
    private RentalService rentalService;

    @ApiOperation(value = "관리자 기기 대여 신청서 리스트 조회")
    @GetMapping("/list")
    public ModelAndView infoList() {
        RentalSearchDto rentalSearchDto = new RentalSearchDto();
        ModelAndView mav = new ModelAndView();
        List<RentalDto> list = rentalService.infoList(rentalSearchDto);
        System.out.println(list.toString());
        mav.addObject("list", list);
        mav.addObject("title", "대여");
        mav.setViewName("/list");//어디로 이동할지 ex) rental/list
        return mav;
    }

    @ApiOperation(value = "관리자 기기 대여 신청서 리스트 조회 검색")
    @PostMapping("/list")
    public ResponseEntity<?> listSearch(@RequestBody RentalSearchDto rentalSearchDto, Model model){
        List<RentalDto> list = rentalService.infoList(rentalSearchDto);
        Map<String, Object> map = new HashMap<>();
        model.addAttribute("list", list);
        model.addAttribute("title", "대여");
        map.put("list", list);
        map.put("title", "대여");
        return ResponseEntity.ok(map);
    }

    @ApiOperation(value = "관리자 기기 대여 신청서 신청")
    @GetMapping("/apply")
    public String apply(Long rental_id, boolean accept) {
        rentalService.apply(rental_id, accept);
        return "";
    }
}
