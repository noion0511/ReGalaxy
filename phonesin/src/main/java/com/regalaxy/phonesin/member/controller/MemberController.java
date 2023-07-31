package com.regalaxy.phonesin.member.controller;

import com.regalaxy.phonesin.member.model.MemberDto;
import com.regalaxy.phonesin.member.model.entity.Member;
import com.regalaxy.phonesin.member.model.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member/signup")
    public ResponseEntity<Member> signUp(@RequestBody MemberDto memberDto) {
        ResponseEntity<Member> savedMember = memberService.signUp(memberDto);
        return new ResponseEntity(savedMember, HttpStatus.OK);
    }

    @GetMapping("/member/login")
    public String login() {
        return "di";
    }
}