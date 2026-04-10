package com.coffee.dto;

// Dto (Data Transfer Object)
// 클라이언트에서 넘겨진 로그인 정보를 저장하기 위한 자바 클래스

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginDto {

    private String email ;
    private String password;


}
