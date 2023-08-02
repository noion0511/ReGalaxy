package com.regalaxy.phonesin.back.controller;

import com.regalaxy.phonesin.back.model.entity.Back;
import com.regalaxy.phonesin.back.model.service.BackService;
import com.regalaxy.phonesin.member.model.SearchDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(value = "어드민 휴대폰 반납 API", description = "어드민 휴대폰 반납 Controller")
public class AdminBackController {

    private final BackService backService;

    // 전체 반납 신청서 조회/검색/페이징 처리
    @ApiOperation(value = "기기 반납 신청서 리스트 조회")
    @GetMapping("/back/list")
    public ResponseEntity<Page<Back>> backList(@RequestBody SearchDto searchDto) {
        Pageable pageable = PageRequest.of(searchDto.getPgno(), 10); // 한 페이지당 10개씩 조회
        Page<Back> backPage = backService.backList(searchDto.getEmail(), searchDto.getIsBlack(), searchDto.getIsCha(), pageable);
        return new ResponseEntity<>(backPage, HttpStatus.OK);
    }
}
