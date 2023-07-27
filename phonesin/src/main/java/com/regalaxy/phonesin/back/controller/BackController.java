package com.regalaxy.phonesin.back.controller;

import com.regalaxy.phonesin.back.model.BackDto;
import com.regalaxy.phonesin.back.model.entity.Back;
import com.regalaxy.phonesin.back.model.service.BackService;
import com.regalaxy.phonesin.phone.model.entity.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BackController {

    private final BackService backService;

    // 반납 신청서 작성하기
    // RequestBody로 JSON 데이터로 받기
    @PostMapping("/back/apply")
    public ResponseEntity<Map<String, Object>> apply(@RequestBody BackDto backDto) {
        Map<String, Object> resultMap = new HashMap<>();
        backService.saveBack(backDto);
        resultMap.put("back", backDto);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    // 반납 신청서 상세 정보보기
    // RequestParam으로 form-data로 받기
    @GetMapping("/back/info")
    public ResponseEntity<Map<String, Object>> backInfo(@RequestParam Long backId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("back", backService.findOne(backId));
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK); // 반납 신청서 상세 정보보기 url 들어오면 적어주기
    }
}
