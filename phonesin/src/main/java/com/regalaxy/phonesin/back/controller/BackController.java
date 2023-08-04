package com.regalaxy.phonesin.back.controller;

import com.regalaxy.phonesin.back.model.BackDto;
import com.regalaxy.phonesin.back.model.service.BackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Map<String, Object>>> apply(@RequestBody List<BackDto> backDtos) {
        List<Map<String, Object>> resultMaps = new ArrayList<>();
        for (int i = 0; i < backDtos.size(); i++) {
            Map<String, Object> resultMap = new HashMap<>();
            backService.apply(backDtos.get(i));
            resultMap.put("back", backDtos.get(i));
            resultMaps.add(resultMap);
        }
        return new ResponseEntity<List<Map<String, Object>>>(resultMaps, HttpStatus.OK);
    }

    // 반납 신청서 상세 정보보기
    @ApiOperation(value = "기기 반납 신청서 상세 조회")
    @GetMapping("/back/info")
    public ResponseEntity<Map<String, Object>> backInfo(@RequestBody BackDto backDto) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("back", backService.backInfo(backDto.getBackId()));
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    // 반납 신청서 수정
    // 나중에 유저는 rentalStatus를 수정하지 못하도록 설정.
    @ApiOperation(value = "기기 반납 신청서 수정")
    @PutMapping("/back/update")
    public ResponseEntity<Map<String, Object>> update(@RequestBody BackDto backDto) {
        Map<String, Object> resultMap = new HashMap<>();
        BackDto updatedBackDto = backService.updateBack(backDto);
        resultMap.put("updatedBack", updatedBackDto);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
}