package com.regalaxy.phonesin.rental.controller;

import com.regalaxy.phonesin.rental.model.ApplyDto;
import com.regalaxy.phonesin.rental.model.service.RentalService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/rental")
@CrossOrigin("*")
public class AdminRentalController {
    private RentalService rentalService;

    @GetMapping("/info/list")
    public String infoList(int pgno){
        return "";
    }

    @GetMapping("/info")
    public String info(int rental_id){
        return "";
    }

    @GetMapping("/apply")
    public String apply(ApplyDto applyDto){
        return "";
    }
}
