package com.regalaxy.phonesin.member.controller;

import com.regalaxy.phonesin.member.model.LoginRequestDto;
import com.regalaxy.phonesin.member.model.MemberAdminDto;
import com.regalaxy.phonesin.member.model.MemberDto;
import com.regalaxy.phonesin.member.model.jwt.JwtTokenProvider;
import com.regalaxy.phonesin.member.model.service.MemberService;
import com.regalaxy.phonesin.rental.model.RentalDto;
import com.regalaxy.phonesin.rental.model.RentalSearchDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/admin")
@Api(tags = "멤버 또는 토큰 관리 API(관리자)", description = "멤버 또는 토큰 관리 Controller(관리자)")
public class AdminMemberController {

    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(value = "관리자가 직접 회원 정보 수정")
    @PutMapping("/update")
    public ResponseEntity<String> updateByAdmin(@RequestBody MemberAdminDto memberAdminDto) {
        Map<String, Object> resultMap = new HashMap<>();
        MemberDto updatedMemberDto = memberService.updateMemberByAdmin(memberAdminDto);
        resultMap.put("updatedMember", updatedMemberDto);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @ApiOperation(value = "회원 정보 상세 조회(관리자)")
    @PostMapping("/info/{memberId}")
    public ResponseEntity<Map<String, Object>> memberInfoByAdmin(@PathVariable("memberId") Long memberId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("member", memberService.UserInfoByAdmin(memberId));
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ModelAndView login(@RequestBody LoginRequestDto loginRequestDto) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("status", "기본 응답");
        try {
            String email = loginRequestDto.getEmail();
            // 이메일과 비밀번호 인증
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, loginRequestDto.getPassword()));

            // 권한 설정
            String authority;
            if (memberService.AdminInfo(loginRequestDto.getEmail()).getIsManager()) {
                authority = "ROLE_ADMIN";
            } else {
                mav.addObject("error", "일반 사용자는 어플을 이용해주세요");
                mav.setViewName("/list");//어디로 이동할지 ex) rental/list
                return mav;
            };

            // 토큰 발급
            Long memberId = memberService.AdminInfo(loginRequestDto.getEmail()).getMemberId();
            String accessToken = jwtTokenProvider.createAccessToken(email, authority, memberId);
            String refreshToken = jwtTokenProvider.createRefreshToken(email);
            memberService.signIn(loginRequestDto, refreshToken);

            mav.addObject("accessToken", accessToken);
            mav.addObject("refreshToken", refreshToken);
            mav.setViewName("/list");//어디로 이동할지 ex) rental/list
            return mav;

        } catch (AuthenticationException e) {
            mav.addObject("error", "이메일 또는 비밀번호가 일치하지 않습니다.");
            mav.setViewName("/login");//어디로 이동할지 ex) rental/list
            return mav;
        }
    }

    @ApiOperation(value = "로그인")
    @GetMapping("/login")
    public String loginpage() {
        return "login";
    }
}
