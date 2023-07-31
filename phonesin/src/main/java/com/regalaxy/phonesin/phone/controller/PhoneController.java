package com.regalaxy.phonesin.phone.controller;

import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.phone.model.PhoneDto;
import com.regalaxy.phonesin.phone.model.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
