package com.regalaxy.phonesin.member.controller;

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
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, loginRequestDto.getPassword()));

            // roles를 빈 리스트로 전달하는데, 이 부분을 적절히 수정.
            String access_token = jwtTokenProvider.createToken(email, new ArrayList<>());
            String refreshToken = jwtTokenProvider.createRefreshToken(email);
            memberService.saveRefreshToken(email, refreshToken);

            Map<String, String> response = new HashMap<>();
            response.put("email", email);
            response.put("accessToken", access_token);
            response.put("refreshToken", refreshToken);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }
    }

    @ApiOperation(value = "액세스 토큰 만료시 리프레시 토큰 재발급")
    @PostMapping("/token/refresh")
    public ResponseEntity<Map<String, String>> refresh(@RequestBody Map<String, String> tokenMap) {
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
}