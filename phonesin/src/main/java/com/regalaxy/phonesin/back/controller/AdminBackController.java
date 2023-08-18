package com.regalaxy.phonesin.back.controller;

import com.regalaxy.phonesin.back.model.BackAdminDto;
import com.regalaxy.phonesin.back.model.BackAdminSearschDto;
import com.regalaxy.phonesin.back.model.BackAdminUpdateDto;
import com.regalaxy.phonesin.back.model.BackDto;
import com.regalaxy.phonesin.back.model.service.BackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/back")
@Api(value = "어드민 휴대폰 반납 API", description = "어드민 휴대폰 반납 Controller")
public class AdminBackController {

    private final BackService backService;

    // 전체 반납 신청서 조회/검색/페이징 처리
    @ApiOperation(value = "기기 반납 신청서 리스트 조회")
    @GetMapping("/list")
    public ModelAndView backList() {
        BackAdminSearschDto backAdminSearschDto = new BackAdminSearschDto();
        List<BackAdminDto> list = backService.list(backAdminSearschDto);
        ModelAndView mav = new ModelAndView();
        mav.addObject("list", list);
        mav.addObject("title", "반납");
        mav.setViewName("list");
        return mav;
    }

    @ApiOperation(value = "관리자 기기 대여 신청서 리스트 조회 검색")
    @PostMapping("/list")
    public ResponseEntity<?> listSearch(@RequestBody BackAdminSearschDto backAdminSearschDto, Model model){
        List<BackAdminDto> list = backService.list(backAdminSearschDto);
        Map<String, Object> map = new HashMap<>();
        model.addAttribute("list", list);
        model.addAttribute("title", "반납");
        map.put("list", list);
        map.put("title", "반납");
        return ResponseEntity.ok(map);
    }

    @ApiOperation(value = "기기 반납 신청서 수정(관리자)")
    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> update(@RequestBody BackAdminUpdateDto backAdminUpdateDto) {
        Map<String, Object> resultMap = new HashMap<>();
        try{
            backService.updateBackByAdmin(backAdminUpdateDto);
            resultMap.put("message", "성공적으로 수정되었습니다.");
            resultMap.put("status", HttpStatus.OK.value());

            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.NOT_FOUND.value());

            return new ResponseEntity<>(resultMap, HttpStatus.NOT_FOUND);
        }
    }
}
