package com.regalaxy.phonesin.member.model.repository;

import com.regalaxy.phonesin.member.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
