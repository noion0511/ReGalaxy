package com.regalaxy.phonesin.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.regalaxy.phonesin.member.model.*;
import com.regalaxy.phonesin.member.model.jwt.JwtTokenProvider;
import com.regalaxy.phonesin.member.model.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody MemberSignUpDto memberSignUpDto) {
        ResponseEntity<String> result = memberService.signUp(memberSignUpDto);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("message", result.getBody());
        resultMap.put("status", result.getStatusCodeValue());
        return new ResponseEntity<Map<String, Object>>(resultMap, result.getStatusCode());
    }

    @ApiOperation(value = "로그인")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequestDto loginRequestDto) {
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
            ResponseEntity<Map<String, Object>> signInResponse = memberService.signIn(loginRequestDto, refreshToken);

            Map<String, Object> response = signInResponse.getBody();
            if (signInResponse.getStatusCodeValue() == 200) {
                response.put("accessToken", accessToken);
                response.put("refreshToken", refreshToken);
            }
            response.put("status", signInResponse.getStatusCodeValue());

            return new ResponseEntity<>(response, signInResponse.getStatusCode());
        } catch (AuthenticationException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "이메일 또는 비밀번호가 일치하지 않습니다.");
            response.put("status", HttpStatus.UNAUTHORIZED.value());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @ApiOperation(value = "액세스 토큰 만료시 리프레시 토큰 재발급")
    @PostMapping("/token/refresh")
    public ResponseEntity<Map<String, Object>> refresh(@RequestBody MemberRefreshTokenDto tokenDto) {
        Map<String, Object> response = new HashMap<>();

        // 현재 리프레시 토큰과 새로운 액세스 토큰
        String refreshToken = tokenDto.getRefreshToken();
        String newAccessToken;
        try {
            newAccessToken = jwtTokenProvider.refreshAccessToken(refreshToken);
        } catch (Exception e) {
            response.put("message", "리프레시 토큰이 올바르지 않습니다.");
            response.put("status", 401);

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        response.put("accessToken", newAccessToken);
        response.put("message", "액세스 토큰을 성공적으로 발급하였습니다.");
        response.put("status", 200);

        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "사용자가 자신의 정보 상세 조회")
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> memberInfo(@ApiIgnore @RequestHeader String authorization) {
        String token = authorization.replace("Bearer ", "");
        Long memberId = jwtTokenProvider.getMemberId(token);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("member", memberService.UserInfo(memberId));
        resultMap.put("message", "성공적으로 조회하였습니다.");
        resultMap.put("status", 200);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "사용자가 회원 정보 수정")
    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> update(@RequestBody MemberUpdateByUserDto memberUpdateByUserDto, @ApiIgnore @RequestHeader String authorization) {
        String token = authorization.replace("Bearer ", "");
        String email = jwtTokenProvider.getEmail(token);
        Map<String, Object> resultMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> updateResponse = memberService.updateMemberByUser(email, memberUpdateByUserDto);

        return updateResponse;
    }

    @ApiOperation(value = "회원 탈퇴")
    @PutMapping("/delete")
    public ResponseEntity<Map<String, Object>> delete(@ApiIgnore @RequestHeader String authorization) {
        Map<String, Object> resultMap = new HashMap<>();
        String token = authorization.replace("Bearer ", "");
        Long memberId = jwtTokenProvider.getMemberId(token);
        memberService.deleteMember(memberId);
        resultMap.put("message", "성공적으로 삭제되었습니다.");
        resultMap.put("status", HttpStatus.OK.value());
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @ApiOperation(value = "차상위 계층 인증")
    @PostMapping("/ischa")
    public ResponseEntity<Map<String, Object>> isCha(@RequestBody MemberIsChaDto memberIsChaDto) {
        Map<String, Object> resultMap = new HashMap<>();
        StringBuffer response = new StringBuffer();
        try {
            // URL 설정
            URL url = new URL("https://www.bokjiro.go.kr/ssis-tbu/TWAL26100M/twatza/certfIssuAplyMng/selectCertfTruflsIdnty.do");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

            Map<String, Object> dmSearch = new HashMap<>();
            dmSearch.put("certfIssuDcd", "08");
            dmSearch.put("aplyRelpsFlnm", memberIsChaDto.getMemberName());
            dmSearch.put("aplyRelpsIdmbIdnfNo", "");
            dmSearch.put("certfIssuNo", memberIsChaDto.getChaCode());
            dmSearch.put("aplyRelpsRrnoBrdt", memberIsChaDto.getBirth());

            Map<String, Object> dmScr = new HashMap<>();
            dmScr.put("curScrId", "tbu/app/twoa/twoae/TWAL26100M");
            dmScr.put("befScrId", "");

            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("dmSearch", dmSearch);
            requestMap.put("dmScr", dmScr);

            // Body 입력
            String requestBody;
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                requestBody = objectMapper.writeValueAsString(requestMap);
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
            resultMap.put("message", "Failure: " + e.getMessage());
            resultMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }


        if (response.toString().split(":")[1].split("}")[0].equals("null")) {
            resultMap.put("message", "요청하신 문서번호는 발급된 내역이 없는 증명서로 확인 되었습니다.");
            resultMap.put("status", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(resultMap, HttpStatus.NOT_FOUND);
        } else {
            resultMap.put("message", response.toString().split(":")[1].split("}")[0]);
            resultMap.put("status", HttpStatus.OK.value());
            // 차상위 인증에 성공했을 경우를 테스트 할 수 없어서 우선 메시지를 띄우도록 설정
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "비밀번호 변경 기능")
    @PutMapping("/update/password")
    public ResponseEntity<Map<String, Object>> updatePassword(@RequestBody MemberUpdatePasswordDto requestDto) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            memberService.changePassword(requestDto);
            resultMap.put("message", "비밀번호가 성공적으로 변경되었습니다.");
            resultMap.put("status", HttpStatus.OK.value());
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "이메일 인증")
    @PostMapping("/email-verification")
    public ResponseEntity<Map<String, Object>> requestEmailVerification(@RequestBody EmailVerificationDto requestDto) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            memberService.requestEmailVerification(requestDto.getEmail());
            resultMap.put("message", "이메일 인증 코드가 발송되었습니다.");
            resultMap.put("status", HttpStatus.OK.value());
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.CONFLICT.value());
            return new ResponseEntity<>(resultMap, HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "이메일 인증 코드 확인")
    @PostMapping("/email-verification/confirm")
    public ResponseEntity<Map<String, Object>> confirmEmailVerification(@RequestBody EmailVerificationConfirmationDto confirmationDto) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            memberService.confirmEmailVerification(confirmationDto.getEmail(), confirmationDto.getCode());
            resultMap.put("message", "이메일이 성공적으로 인증되었습니다.");
            resultMap.put("status", HttpStatus.OK.value());
            return new ResponseEntity<>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            resultMap.put("message", e.getMessage());
            resultMap.put("status", HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
        }
    }
}