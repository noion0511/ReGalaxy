package com.regalaxy.phonesin.phone.controller;

import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.phone.model.PhoneApplyDto;
import com.regalaxy.phonesin.phone.model.PhoneDto;
import com.regalaxy.phonesin.phone.model.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/admin/phone")
@CrossOrigin("*")
public class PhoneController {
    @Autowired
    private PhoneService phoneService;
    @GetMapping("/list")
    public ModelAndView list(SearchDto searchDto){
        ModelAndView mav = new ModelAndView();
        List<PhoneDto> list = phoneService.list(searchDto);
        mav.addObject("list", list);
        mav.setViewName("");//어디로 이동할지 ex) rental/list
        System.out.println("Success");
        return null;
    }

    @GetMapping("/info")
    public ModelAndView info(Long phone_id){
        ModelAndView mav = new ModelAndView();
        PhoneDto phoneDto = phoneService.info(phone_id);
        mav.addObject("info", phoneDto);
        mav.setViewName("");//어디로 이동할지 ex) rental/list
        System.out.println("Success");
        return null;
    }

    @PostMapping("/apply")
    public ModelAndView apply(PhoneApplyDto phoneApplyDto){
        ModelAndView mav = new ModelAndView();
        phoneService.apply(phoneApplyDto);
        mav.setViewName("");//어디로 이동할지 ex) rental/list
        System.out.println("Success");
        return null;
    }

}
