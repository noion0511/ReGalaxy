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

import java.util.*;

@RestController
@RequiredArgsConstructor
@Api(value = "휴대폰 반납 API", description = "휴대폰 반납 Controller")
public class BackController {

    private final BackService backService;

    // 반납 신청서 작성하기
    // RequestBody로 JSON 데이터로 받기
    @ApiOperation(value = "기기 반납 신청서 작성")
    @PostMapping("/back/apply")
    public ResponseEntity<Map<String, Object>> apply(@RequestBody List<BackDto> backDtos) {
        Set<Long> rentalIds = new HashSet<>();
        Map<String, Object> resultMap = new HashMap<>();

        for (BackDto backDto : backDtos) {
            if (rentalIds.contains(backDto.getRentalId())) {
                resultMap.put("message", "rentalId가 중복되었습니다.");
                resultMap.put("status", HttpStatus.BAD_REQUEST.value());

                return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
            }
            rentalIds.add(backDto.getRentalId());

            backService.apply(backDto);
        }
        resultMap.put("message", "성공적으로 작성되었습니다.");
        resultMap.put("status", HttpStatus.OK.value());
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    // 반납 신청서 상세 정보보기
    @ApiOperation(value = "기기 반납 신청서 상세 조회")
    @GetMapping("/back/info/{backId}")
    public ResponseEntity<Map<String, Object>> backInfo(@PathVariable Long backId, @ApiIgnore @RequestHeader String authorization) {
        String token = authorization.replace("Bearer ", "");

        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap.put("data", backService.backInfo(backId, token));
            resultMap.put("message", "성공적으로 조회하였습니다.");
            resultMap.put("status", HttpStatus.OK.value());

            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.FORBIDDEN.value());

            return new ResponseEntity<>(resultMap, HttpStatus.FORBIDDEN);
        }
    }

    // 반납 신청서 수정
    @ApiOperation(value = "기기 반납 신청서 수정")
    @PutMapping("/back/update")
    public ResponseEntity<Map<String, Object>> update(@RequestBody BackUserDto backUserDto, @ApiIgnore @RequestHeader String authorization) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            backService.updateBackByUser(backUserDto, authorization);
            resultMap.put("message", "성공적으로 수정하였습니다.");
            resultMap.put("status", HttpStatus.OK.value());

            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.FORBIDDEN.value());

            return new ResponseEntity<>(resultMap, HttpStatus.FORBIDDEN);
        }
    }
}