package com.coffee.service;

import com.coffee.entity.Member;
import com.coffee.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
public class tt {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void reencodeAllPasswords() {
        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            String pw = member.getPassword();
            if (pw != null && !pw.startsWith("$2a$") && !pw.startsWith("$2b$")) {
                member.setPassword(passwordEncoder.encode(pw));
                memberRepository.save(member);
                System.out.println(member.getEmail() + " → 비밀번호 재인코딩 완료");
            }
        }
    }
}
