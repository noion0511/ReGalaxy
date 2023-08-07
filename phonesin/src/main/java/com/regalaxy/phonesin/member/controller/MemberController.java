package com.regalaxy.phonesin.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.regalaxy.phonesin.member.model.*;
import com.regalaxy.phonesin.member.model.jwt.JwtTokenProvider;
import com.regalaxy.phonesin.member.model.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
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
    public ResponseEntity<String> signUp(@RequestBody MemberDto memberDto) {
        memberService.signUp(memberDto);
        return ResponseEntity.ok("Success");
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
            MemberAdminDto userInfo = memberService.AdminInfo(loginRequestDto.getEmail());
            if (userInfo.getIsManager()) {
                authority = "ROLE_ADMIN";
            } else {
                authority = "ROLE_USER";
            };

            // 토큰 발급
            Long memberId = userInfo.getMemberId();
            String accessToken = jwtTokenProvider.createAccessToken(email, authority, memberId);
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

    @ApiOperation(value = "사용자가 자신의 정보 상세 조회")
    @PostMapping("/info/{memberId}")
    public ResponseEntity<Map<String, Object>> memberInfo(@PathVariable("memberId") Long memberId, @ApiIgnore @RequestHeader String authorization) {
        String token = authorization.replace("Bearer ", "");
        Map<String, Object> resultMap = new HashMap<>();
        if (jwtTokenProvider.getMemberId(token) != memberId) {
            throw new IllegalStateException("멤버 ID가 일치하지 않습니다.");
        };
        resultMap.put("member", memberService.UserInfo(memberId));
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "사용자가 회원 정보 수정")
    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody MemberUserDto memberUserDto, @ApiIgnore @RequestHeader String authorization) {
        String token = authorization.replace("Bearer ", "");
        Map<String, Object> resultMap = new HashMap<>();
        if (!jwtTokenProvider.getEmail(token).equals(memberUserDto.getEmail())) {
            throw new IllegalStateException("Email이 일치하지 않습니다.");
        }
        MemberDto updatedMemberDto = memberService.updateMemberByUser(memberUserDto);
        resultMap.put("updatedMember", updatedMemberDto);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @ApiOperation(value = "회원 탈퇴")
    @PutMapping("/delete/{memberId}")
    public ResponseEntity<String> delete(@PathVariable("memberId") Long memberId, @ApiIgnore @RequestHeader String authorization) {
        String token = authorization.replace("Bearer ", "");
        if (jwtTokenProvider.getMemberId(token) != memberId) {
            throw new IllegalStateException("멤버 ID가 일치하지 않습니다.");
        }
        memberService.deleteMember(memberId);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @ApiOperation(value = "차상위 계층 인증")
    @PostMapping("/ischa")
    public ResponseEntity<String> isCha(@RequestBody Map<String, Object> requestMap) {
        StringBuffer response = new StringBuffer();
        try {
            // URL 설정
            URL url = new URL("https://www.bokjiro.go.kr/ssis-tbu/TWAL26100M/twatza/certfIssuAplyMng/selectCertfTruflsIdnty.do");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

            // Body 입력
            String requestBody;
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                requestBody =  objectMapper.writeValueAsString(requestMap);
            } catch (Exception e) {
                e.printStackTrace();
                requestBody = null;
            }

            BufferedWriter in = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            in.write(requestBody);
            in.flush();
            in.close();

            // response 출력
            Charset charset = Charset.forName("UTF-8");
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
            String inputLine;

            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            System.out.println("Response: " + response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Failure: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (response.toString().split(":")[1].split("}")[0].equals("null")) {
            return new ResponseEntity<String>("요청하신 문서번호는 발급된 내역이 없는 증명서로 확인 되었습니다.", HttpStatus.OK);
        } else {
            // 차상위 인증에 성공했을 경우를 테스트 할 수 없어서 우선 메시지를 띄우도록 설정
            return new ResponseEntity<String>(response.toString().split(":")[1].split("}")[0], HttpStatus.OK);
        }
    }

    @ApiOperation(value = "비밀번호 변경 기능")
    @PutMapping("/update/password")
    public ResponseEntity<String> updatePassword(@RequestBody MemberUpdatePasswordDto requestDto) {
        try {
            memberService.changePassword(requestDto);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "이메일 인증")
    @PostMapping("/email-verification")
    public ResponseEntity<String> requestEmailVerification(@RequestBody EmailVerificationConfirmationDto requestDto) {
        memberService.requestEmailVerification(requestDto.getEmail());
        return new ResponseEntity<>("이메일 인증 코드가 발송되었습니다.", HttpStatus.OK);
    }

    @ApiOperation(value = "이메일 인증 코드 확인")
    @PostMapping("/email-verification/confirm")
    public ResponseEntity<String> confirmEmailVerification(@RequestBody EmailVerificationConfirmationDto confirmationDto) {
        memberService.confirmEmailVerification(confirmationDto.getEmail(), confirmationDto.getCode());
        return new ResponseEntity<>("이메일이 성공적으로 인증되었습니다.", HttpStatus.OK);
    }
}