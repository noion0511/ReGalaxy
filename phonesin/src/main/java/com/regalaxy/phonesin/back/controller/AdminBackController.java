package com.regalaxy.phonesin.back.controller;

import com.regalaxy.phonesin.back.model.BackAdminDto;
import com.regalaxy.phonesin.back.model.entity.Back;
import com.regalaxy.phonesin.back.model.service.BackService;
import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.phone.model.PhoneDto;
import com.regalaxy.phonesin.phone.model.PhoneSearchDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Api(value = "어드민 휴대폰 반납 API", description = "어드민 휴대폰 반납 Controller")
public class AdminBackController {

    private final BackService backService;

    // 전체 반납 신청서 조회/검색/페이징 처리
    @ApiOperation(value = "기기 반납 신청서 리스트 조회")
    @GetMapping("/admin/back/list")
    public ModelAndView backList() {
        List<BackAdminDto> list = backService.list();
        ModelAndView mav = new ModelAndView();
        mav.addObject("list", list);
        mav.addObject("title", "반납");
        mav.setViewName("/list");
        return mav;
    }
}
