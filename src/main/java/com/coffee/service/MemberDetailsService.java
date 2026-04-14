package com.coffee.service;

import com.coffee.entity.Member;
import com.coffee.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service // 객체 생성해야함.
@RequiredArgsConstructor // final에 붙는 @.
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override // 스프링 시큐리티가 인증 처리를 시작하면서 이 메소드를 자동으로 호출합니다.
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            String message = "이메일이 " + email + "인 회원은 존재하지 않습니다.";
            //자바에서 사용자가 예외를 발생시키고자 할 경우에 throw 키워드 사용.
            throw new UsernameNotFoundException(message);
        } else {
            System.out.println("loadUserByUsername() 메소드");
            System.out.println(member);
        }
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .authorities("ROLE_" + member.getRole().name()) // ROLE_USER / ROLE_ADMIN
                .build();
    }
}
