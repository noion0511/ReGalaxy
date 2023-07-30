package com.regalaxy.phonesin.member.controller;

import com.regalaxy.phonesin.member.model.MemberDto;
import com.regalaxy.phonesin.member.model.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member/signup")
    public ResponseEntity<MemberDto> signUp(@RequestBody MemberDto memberDto) {
        MemberDto savedMember = memberService.signUp(memberDto);
        return new ResponseEntity<MemberDto>(savedMember, HttpStatus.OK);
    }
}