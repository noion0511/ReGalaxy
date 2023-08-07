package com.regalaxy.phonesin.rental.controller;

import com.regalaxy.phonesin.phone.model.PhoneDto;
import com.regalaxy.phonesin.phone.model.PhoneSearchDto;
import com.regalaxy.phonesin.rental.model.AdminRentalApplyDto;
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
        mav.addObject("back", false);
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
        model.addAttribute("back", null);
        map.put("list", list);
        map.put("title", "대여");
        map.put("back", false);
        return ResponseEntity.ok(map);
    }

    @ApiOperation(value = "관리자 기기 대여 신청서 신청")
    @PostMapping("/apply")
    public ResponseEntity<?> apply(@RequestBody AdminRentalApplyDto adminRentalApplyDto, Model model) {
        List<RentalDto> list = rentalService.apply(adminRentalApplyDto);
        Map<String, Object> map = new HashMap<>();
        model.addAttribute("list", list);
        model.addAttribute("title", "대여");
        model.addAttribute("back", null);
        map.put("list", list);
        map.put("title", "대여");
        map.put("back", false);
        if(list==null){
            model.addAttribute("back", "대여할 휴대폰이 없습니다.");
            map.put("back", true);

            RentalSearchDto rentalSearchDto = new RentalSearchDto();
            rentalSearchDto.setReady(adminRentalApplyDto.isReady());
            ModelAndView mav = new ModelAndView();
            list = rentalService.infoList(rentalSearchDto);
            model.addAttribute("list", list);
            map.put("list", list);
        }
        return ResponseEntity.ok(map);
    }
}
