package com.regalaxy.phonesin.back.controller;

import com.regalaxy.phonesin.back.model.entity.Back;
import com.regalaxy.phonesin.back.model.service.BackService;
import com.regalaxy.phonesin.phone.model.entity.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BackController {

    private final BackService backService;

    @GetMapping("back/findone")
    public String finding(Model model) {
        Back back = backService.findOne(1L);
        System.out.println(back);

        return "redirect:/items";
    }
}
