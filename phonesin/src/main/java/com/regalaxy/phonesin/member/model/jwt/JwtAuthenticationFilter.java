package com.regalaxy.phonesin.member.model.jwt;

import com.regalaxy.phonesin.member.model.exception.NotAdminException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    // 토큰이 유효한지 검사
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        String path = httpRequest.getRequestURI();
        String token = jwtTokenProvider.resolveToken(httpRequest);

        if (path.startsWith("/admin") && !path.startsWith("/admin/member/login")){
            if (!jwtTokenProvider.getIsManager(token)) {
                throw new NotAdminException("관리자만 접근 가능합니다.");
            }
        }

        if (!path.startsWith("/member/login") &&
                !path.startsWith("/member/signup") &&
                !path.startsWith("/member/token/refresh") &&
                !path.startsWith("/admin/member/login") &&
                !path.startsWith("/v3/api-docs") &&
                !path.equals("/") &&
                !path.startsWith("/assets") &&
                !path.startsWith("/images") &&
                !path.startsWith("/error") &&
                !path.startsWith("/swagger-ui") &&
                !path.startsWith("/swagger-resources") &&
                !path.startsWith("/download/phonegojisin")) {


            if (token != null) {
                try {
                    jwtTokenProvider.validateToken(token);
                } catch (Exception e) {
                    // 토큰이 유효하지 않을 때
                    HttpServletResponse httpResponse = (HttpServletResponse) res;
                    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized status
                    httpResponse.setContentType("application/json; charset=utf8");
                    httpResponse.getWriter().write("토큰이 만료되었거나 유효하지 않습니다.");
                    return; // 추가 처리 중지
                }

                Authentication auth = jwtTokenProvider.getAuthentication(token);
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } else {
                // 토큰이 유효하지 않을 때
                HttpServletResponse httpResponse = (HttpServletResponse) res;
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized status
                httpResponse.setContentType("application/json; charset=utf8");
                httpResponse.getWriter().write("로그인이 필요한 서비스입니다.");
                return; // 추가 처리 중지
            }
        }
        filterChain.doFilter(req, res);
    }
}
