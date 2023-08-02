package com.regalaxy.phonesin.phone.controller;

import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.phone.model.PhoneApplyDto;
import com.regalaxy.phonesin.phone.model.PhoneDto;
import com.regalaxy.phonesin.phone.model.service.PhoneService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/admin/phone")
@CrossOrigin("*")
public class PhoneController {
    @Autowired
    private PhoneService phoneService;
    @ApiOperation(value = "휴대폰 목록 조회")
    @GetMapping("/list")
    public ModelAndView list(@RequestBody SearchDto searchDto){
        ModelAndView mav = new ModelAndView();
        List<PhoneDto> list = phoneService.list(searchDto);
        mav.addObject("list", list);
        mav.setViewName("");//어디로 이동할지 ex) rental/list
        if(list != null) {
            System.out.println("Success");
            System.out.println(list.toString());
        }
        return null;
    }
    @ApiOperation(value = "휴대폰 상세 조회")
    @GetMapping("/info")
    public ModelAndView info(Long phone_id){
        ModelAndView mav = new ModelAndView();
        PhoneDto phoneDto = phoneService.info(phone_id);
        mav.addObject("info", phoneDto);
        mav.setViewName("");//어디로 이동할지 ex) rental/list
        System.out.println("Success");
        System.out.println(phoneDto.getPhoneId());
        return null;
    }
    @ApiOperation(value = "휴대폰 정보 등록")
    @PostMapping("/apply")
    public ModelAndView apply(@RequestBody PhoneApplyDto phoneApplyDto){
        ModelAndView mav = new ModelAndView();
        System.out.println("???? : " + phoneApplyDto.isClimateHumidity());
        phoneService.apply(phoneApplyDto);
        mav.setViewName("");//어디로 이동할지 ex) rental/list
        System.out.println("Success");
        return null;
    }
    @ApiOperation(value = "휴대폰 정보 수정")
    @PutMapping("/update")
    public ModelAndView update(@RequestBody PhoneApplyDto phoneApplyDto){
        ModelAndView mav = new ModelAndView();
        phoneService.update(phoneApplyDto);
        mav.setViewName("");//어디로 이동할지 ex) rental/list
        System.out.println("Success");
        return null;
    }
    @ApiOperation(value = "휴대폰 정보 삭제")
    @DeleteMapping("/delete")
    public ModelAndView delete(Long phone_id){
        ModelAndView mav = new ModelAndView();
        phoneService.delete(phone_id);
        mav.setViewName("");//어디로 이동할지 ex) rental/list
        System.out.println("Success");
        return null;
    }

}
