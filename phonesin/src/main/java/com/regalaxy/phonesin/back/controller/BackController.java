package com.regalaxy.phonesin.back.controller;

import com.regalaxy.phonesin.back.model.BackDto;
import com.regalaxy.phonesin.back.model.entity.Back;
import com.regalaxy.phonesin.back.model.service.BackService;
import com.regalaxy.phonesin.phone.model.entity.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class BackController {

    private final BackService backService;

    @PostMapping("/back/apply")
    public String create(BackDto backDto) {
        Back back = new Back();
    }

    // 반납 신청서 상세 정보보기
    @GetMapping("/back/info")
    public String finding(Model model) {
        Back back = backService.findOne(1L);
        return null; // 반납 신청서 상세 정보보기 url 들어오면 적어주기
    }
}
