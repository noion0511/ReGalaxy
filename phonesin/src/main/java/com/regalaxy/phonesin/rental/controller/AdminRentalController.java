package com.regalaxy.phonesin.rental.controller;

import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.rental.model.RentalDetailDto;
import com.regalaxy.phonesin.rental.model.RentalDto;
import com.regalaxy.phonesin.rental.model.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/admin/rental")
@CrossOrigin("*")
public class AdminRentalController {
    @Autowired
    private RentalService rentalService;

    @GetMapping("/info/list")
    public ModelAndView infoList(SearchDto searchDto){
        ModelAndView mav = new ModelAndView();
        List<RentalDto> list = rentalService.infoList(searchDto);
        mav.addObject("list", list);
        mav.addObject("pgno", searchDto.getPgno());
        mav.setViewName("");//어디로 이동할지 ex) rental/list
        System.out.print("Success");
        return mav;
    }

    @GetMapping("/info")
    public String info(int rental_id){
        RentalDetailDto rentalDetailDto = rentalService.info(rental_id);
        return "";
    }

    @GetMapping("/apply")
    public String apply(Long rental_id, boolean accept){
        rentalService.apply(rental_id, accept);
        return "";
    }
}
