package com.regalaxy.phonesin.back.controller;

import com.regalaxy.phonesin.back.model.BackDto;
import com.regalaxy.phonesin.back.model.BackInfoDto;
import com.regalaxy.phonesin.back.model.BackUserDto;
import com.regalaxy.phonesin.back.model.service.BackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Api(value = "휴대폰 반납 API", description = "휴대폰 반납 Controller")
public class BackController {

    private final BackService backService;

    // 반납 신청서 작성하기
    // RequestBody로 JSON 데이터로 받기
    @ApiOperation(value = "기기 반납 신청서 작성")
    @PostMapping("/back/apply")
    public ResponseEntity<String> apply(@RequestBody List<BackDto> backDtos) {
        List<Map<String, Object>> resultMaps = new ArrayList<>();
        for (int i = 0; i < backDtos.size(); i++) {
            Map<String, Object> resultMap = new HashMap<>();
            backService.apply(backDtos.get(i));
            resultMap.put("back", backDtos.get(i));
            resultMaps.add(resultMap);
        }
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    // 반납 신청서 상세 정보보기
    @ApiOperation(value = "기기 반납 신청서 상세 조회")
    @GetMapping("/back/info")
    public ResponseEntity<Map<String, Object>> backInfo(@RequestBody BackDto backDto, @ApiIgnore @RequestHeader String authorization) {
        String token = authorization.replace("Bearer ", "");

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("back", backService.backInfo(backDto, token));
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    // 반납 신청서 수정
    @ApiOperation(value = "기기 반납 신청서 수정")
    @PutMapping("/back/update")
    public ResponseEntity<Map<String, Object>> update(@RequestBody BackUserDto backUserDto, @RequestHeader String authorization) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            backService.updateBackByUser(backUserDto, authorization);
            resultMap.put("message", "성공적으로 수정하였습니다.");
            resultMap.put("status", HttpStatus.OK.value());

            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.NOT_FOUND.value());

            return new ResponseEntity<>(resultMap, HttpStatus.NOT_FOUND);
        }
    }
}