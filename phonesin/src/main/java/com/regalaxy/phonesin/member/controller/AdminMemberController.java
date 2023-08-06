package com.regalaxy.phonesin.member.controller;

import com.regalaxy.phonesin.member.model.MemberAdminDto;
import com.regalaxy.phonesin.member.model.MemberDto;
import com.regalaxy.phonesin.member.model.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/member")
@Api(tags = "멤버 또는 토큰 관리 API(관리자)", description = "멤버 또는 토큰 관리 Controller(관리자)")
public class AdminMemberController {

    private final MemberService memberService;

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
}
