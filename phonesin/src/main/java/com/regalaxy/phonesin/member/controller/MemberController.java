package com.regalaxy.phonesin.member.controller;

import com.regalaxy.phonesin.back.model.BackDto;
import com.regalaxy.phonesin.member.model.LoginRequestDto;
import com.regalaxy.phonesin.member.model.MemberDto;
import com.regalaxy.phonesin.member.model.entity.Member;
import com.regalaxy.phonesin.member.model.jwt.JwtTokenProvider;
import com.regalaxy.phonesin.member.model.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Api(tags = "멤버 또는 토큰 관리 API", description = "멤버 또는 토큰 관리 Controller")
public class MemberController {

    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<Member> signUp(@RequestBody MemberDto memberDto) {
        ResponseEntity<Member> savedMember = memberService.signUp(memberDto);
        return new ResponseEntity(savedMember, HttpStatus.OK);
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            String email = loginRequestDto.getEmail();
            // 이메일과 비밀번호 인증
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, loginRequestDto.getPassword()));

            // 권한 설정
            String authority;
            if (memberService.Info(loginRequestDto.getEmail()).getIsManager()) {
                authority = "ROLE_ADMIN";
            } else {
                authority = "ROLE_USER";
            };

            // 토큰 발급
            String accessToken = jwtTokenProvider.createAccessToken(email, authority);
            String refreshToken = jwtTokenProvider.createRefreshToken(email);
            memberService.signIn(loginRequestDto, refreshToken);

            Map<String, String> response = new HashMap<>();
            response.put("email", email);
            response.put("accessToken", accessToken);
            response.put("refreshToken", refreshToken);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }
    }

    @ApiOperation(value = "액세스 토큰 만료시 리프레시 토큰 재발급")
    @PostMapping("/token/refresh")
    public ResponseEntity<Map<String, String>> refresh(@RequestBody Map<String, String> tokenMap) {

        // 현재 리프레시 토큰과 새로운 액세스 토큰
        String refreshToken = tokenMap.get("refreshToken");
        String newAccessToken = jwtTokenProvider.refreshAccessToken(refreshToken);

        Map<String, String> response = new HashMap<>();
        response.put("accessToken", newAccessToken);

        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "회원 정보 상세 조회")
    @PostMapping("/info")
    public ResponseEntity<Map<String, Object>> memberInfo(@RequestBody MemberDto memberDto) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("member", memberService.Info(memberDto.getEmail()));
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "회원 정보 수정")
    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> update(@RequestBody MemberDto memberDto) {
        Map<String, Object> resultMap = new HashMap<>();
        MemberDto updatedMemberDto = memberService.updateMember(memberDto);
        resultMap.put("updatedMember", updatedMemberDto);
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "회원 탈퇴")
    @PutMapping("/delete")
    public String delete(Long memberId) {
        memberService.deleteMember(memberId);
        return "성공적으로 삭제되었습니다.";
    }
}