package com.regalaxy.phonesin.rental.controller;

import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.rental.model.RentalDetailDto;
import com.regalaxy.phonesin.rental.model.RentalDto;
import com.regalaxy.phonesin.rental.model.service.RentalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/admin/rental")
@CrossOrigin("*")
@Api(value = "관리자 휴대폰 대여 API", description = "관리자 휴대폰 대여 Controller")
public class AdminRentalController {

    @Autowired
    private RentalService rentalService;

    @ApiOperation(value = "관리자 기기 대여 신청서 리스트 조회")
    @PutMapping("/info/list")
    public ModelAndView infoList(@RequestBody SearchDto searchDto) {
        ModelAndView mav = new ModelAndView();
        List<RentalDto> list = rentalService.infoList(searchDto);
        System.out.print("Success");
        mav.addObject("list", list);
        mav.setViewName("");//어디로 이동할지 ex) rental/list
        return mav;
    }

    @ApiOperation(value = "관리자 기기 대여 신청서 신청")
    @GetMapping("/apply")
    public String apply(Long rental_id, boolean accept) {
        rentalService.apply(rental_id, accept);
        return "";
    }
}
