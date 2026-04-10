package com.coffee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean //Spring Security 가 이 Bean을 읽어서 Cors 정책으로 사용합니다.
    public CorsConfigurationSource corsConfigurationSource(){

        // configuration 객체는 클라이언트로부터 요쳥이 들어왔을때 CORS 정책을 적용시켜주는 객체입니다.
        CorsConfiguration configuration = new CorsConfiguration();

        // 리액트의 포트번호를 여기에 작성
        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "http://127.0.0.1:5173"
        ));
        configuration.setAllowedMethods(List.of(
                "GET","POST","PUT","DELETE","OPTIONS","PATCH"
        ));
        //  "Authorization"는 axioInstance.tsx 파일참고
        //  "Content-Type" 는 LoginPage.tsx 참고
        //  "Accept"
        configuration.addAllowedHeader("Authorization");
        configuration.addAllowedHeader("Content-Type");
        configuration.addAllowedHeader("Accept");
        //쿠키 Authorization 헤더 포함 요청 허용
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source
                =new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
