package com.regalaxy.phonesin.member.controller;

import com.regalaxy.phonesin.member.model.LoginRequestDto;
import com.regalaxy.phonesin.member.model.MemberAdminDto;
import com.regalaxy.phonesin.member.model.MemberDto;
import com.regalaxy.phonesin.member.model.jwt.JwtTokenProvider;
import com.regalaxy.phonesin.member.model.service.MemberService;
import com.regalaxy.phonesin.member.model.MemberSearchDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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
@RequestMapping("/admin/member")
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
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDto loginRequestDto) {
        Map<String, String> response = new HashMap<>();
        try {
            String email = loginRequestDto.getEmail();
            // 이메일과 비밀번호 인증
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, loginRequestDto.getPassword()));

            // 권한 설정
            String authority;
            System.out.println(memberService.AdminInfo(loginRequestDto.getEmail()).getIsManager());
            if (memberService.AdminInfo(loginRequestDto.getEmail()).getIsManager()) {
                authority = "ROLE_ADMIN";
            } else {
                response.put("error", "일반 회원입니다.");
                return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
            };

            // 토큰 발급
            Long memberId = memberService.AdminInfo(loginRequestDto.getEmail()).getMemberId();
            String accessToken = jwtTokenProvider.createAccessToken(email, authority, memberId);
            String refreshToken = jwtTokenProvider.createRefreshToken(email);
            memberService.signIn(loginRequestDto, refreshToken);

            response.put("email", email);
            response.put("accessToken", accessToken);
            response.put("refreshToken", refreshToken);

            return new ResponseEntity(response, HttpStatus.OK);
        } catch (AuthenticationException e) {
            response.put("error", "이메일 또는 비밀번호가 일치하지 않습니다.");
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }
    @ApiOperation(value = "로그인 페이지 이동")
    @GetMapping("/login")
    public String loginpage() {
        return "login";
    }

    @ApiOperation(value = "회원 목록 조회")
    @GetMapping("/list")
    public ModelAndView list(){
        MemberSearchDto memberSearchDto = new MemberSearchDto();
        List<MemberDto> list = memberService.list(memberSearchDto);
        ModelAndView mav = new ModelAndView();
        mav.addObject("list", list);
        mav.addObject("title", "회원");
        mav.setViewName("/list");
        return mav;
    }

    @ApiOperation(value = "회원 목록 조회 검색")
    @PostMapping("/list")
    public ResponseEntity<?> listSearch(@RequestBody MemberSearchDto memberSearchDto, Model model){
        List<MemberDto> list = memberService.list(memberSearchDto);
        Map<String, Object> map = new HashMap<>();
        model.addAttribute("list", list);
        model.addAttribute("title", "회원");
        map.put("list", list);
        map.put("title", "회원");
        return ResponseEntity.ok(map);
    }

    @ApiOperation(value = "블랙리스트 설정")
    @PutMapping("/blacklist/{email}")
    public ResponseEntity<?> blacklist(@PathVariable("email") String email, @RequestBody MemberSearchDto memberSearchDto, Model model) {
        memberService.blackListMember(email);
        List<MemberDto> list = memberService.list(memberSearchDto);
        Map<String, Object> map = new HashMap<>();
        model.addAttribute("list", list);
        model.addAttribute("title", "회원");
        map.put("list", list);
        map.put("title", "회원");
        return ResponseEntity.ok(map);
    }
}
