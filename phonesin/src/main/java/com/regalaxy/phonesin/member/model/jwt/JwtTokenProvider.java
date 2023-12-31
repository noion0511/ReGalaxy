package com.regalaxy.phonesin.member.model.jwt;

import com.regalaxy.phonesin.member.model.exception.JwtAuthenticationException;
import com.regalaxy.phonesin.member.model.repository.MemberRepository;
import com.regalaxy.phonesin.member.model.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsServiceImpl userDetailsService;
    private final MemberRepository memberRepository;
    private String secretKey = "s1s2a3f4y@"; // 비밀키
    private long validityInMilliseconds = 3600000; // 1 hour

    public String createAccessToken(String email, String authority, Long memberId) {

        // email과 권한 정보 claims에 담기
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("memberId", memberId);
        claims.put("authority", authority);

        // 만료 시간 계산
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now) // 토큰 발급 시간
                .setExpiration(validity) // 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey) // 비밀키를 HS256 방식으로 암호화
                .compact();
    }

    public Authentication getAuthentication(String token) {
        // 토큰을 통해 유저 정보 가져오기
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getEmail(token));
        // JWT에서는 비밀번호 정보가 필요없기 때문에 빈 문자열 넣음
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰을 복호화하여 claims를 뽑아내는 과정
    // 리프레시 토큰의 claims에는 이메일만 있음
    public String getEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public Long getMemberId(String token) {
        return Long.valueOf(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("memberId").toString());
    }

    public Boolean getIsManager(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        String string = claims.get("authorities").toString();
        if (string.equals("ROLE_ADMIN")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validateToken(String token) {
        try {
            // parseClaimsJws : 파싱된 서명이 유효한지, 만료되진 않았는지
            // 유효성 검사에 통과하면 true를 반환한다.
            // 유효성 검사에 통과하지 못하면 예외발생
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }

    // authorization 안에 있는 bearer(token) 반환.
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.replace("Bearer ", "");
        }
        return null;
    }

    public String createRefreshToken(String email) {
        // 리프레시 토큰의 claims에는 권한 정보를 담지 않는다.
        // 이유는 유효기간이 길어서, 탈취당할 경우 관리자 권한이 악용될 가능성이 높기 때문
        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();

        // 리프레시 토큰 유효기간 (우선 일주일 잡음)
        Date validity = new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now) // 토큰 발급 시간
                .setExpiration(validity) // 만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey) // 비밀키를 HS256 방식으로 암호화
                .compact();
    }

    // 리프레시 토큰을 가지고 액세스 토큰 재발급
    public String refreshAccessToken(String refreshToken) {
        String email = getEmail(refreshToken);

        // 발급된 리프레시 토큰에 담겨있는 이메일로 DB에 저장된 리프레시 토큰.
        String storedRefreshToken = memberRepository.findByEmail(email).get().getRefreshToken();

        // 권한 정보 (액세스 토큰을 발급받기 위함)
        String authority = memberRepository.findByEmail(getEmail(refreshToken)).get().getIsManager() ? "ROLE_ADMIN":"ROLE_USER";

        if (storedRefreshToken == null || !refreshToken.equals(storedRefreshToken)) {
            throw new JwtAuthenticationException("리프레시 토큰이 유효하지 않습니다.");
        }

        if (isTokenExpired(refreshToken)) {
            throw new JwtAuthenticationException("리프레시 토큰이 만료되었습니다.");
        }

        Long memberId = memberRepository.findByEmail(email).get().getMemberId();

        return createAccessToken(email, authority, memberId);
    }

    public boolean isTokenExpired(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims.getExpiration().before(new Date());
    }
}