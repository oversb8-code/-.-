package com.coffee.repository;

import com.coffee.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<관리하고자하는엔터티이름, 엔터티의기본키타입>
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 이메일을 사용하여 회원 정보를 조회하는 추상 메소드
    Member findByEmail(String email);
}
