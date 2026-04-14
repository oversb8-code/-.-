package com.coffee.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override // 요청이 들어올 때 마다 컨트롤러에 앞서서 먼저 실행이 되는 메소드입니다.
    protected void doFilterInternal(
            HttpServletRequest request, // 요청
            HttpServletResponse response, // 응답
            FilterChain filterChain) throws ServletException, IOException {

// 1. 현재 요청된 주소를 확인합니다.
        String path = request.getRequestURI();
        System.out.println("현재 요청 주소: " + path);
        System.out.println("aaaaaaa"+request.getHeader("Authorization"));

        // 2. [핵심] 로그인과 회원가입은 토큰 검사 없이 무조건 통과!
        // path가 "/member/login"으로 시작하거나 정확히 일치하는지 확인합니다.
        if (path.contains("/member/login") || path.contains("/member/signup")) {
            filterChain.doFilter(request, response);
            return; // 여기서 필터 로직을 끝내야 아래 토큰 검사 코드로 안 내려갑니다.
        }

        String bearer = request.getHeader("Authorization");

        if (bearer != null && bearer.startsWith("Bearer ")) {
            String token = bearer.substring("Bearer ".length());
            System.out.println("ㅅ11111111111111111111ㄱ");
            if (jwtTokenProvider.validateToken(token)){
                System.out.println("ㅅ2222222222222222222ㄱ");
                String email = jwtTokenProvider.getEmail(token);
                Claims claims = jwtTokenProvider.getClaims(token);
                String role = claims.get("role", String.class);
                System.out.println("이메일 : " + email);
                // 권한 객체 생성
                List<GrantedAuthority> authorities
                        = List.of(new SimpleGrantedAuthority("ROLE_" + role));

                // 인증 객체 생성
                UsernamePasswordAuthenticationToken auth
                        = new UsernamePasswordAuthenticationToken(email, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
}