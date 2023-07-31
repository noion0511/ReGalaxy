package com.regalaxy.phonesin.member.model.service;

import com.regalaxy.phonesin.member.model.LoginRequestDto;
import com.regalaxy.phonesin.member.model.MemberDto;
import com.regalaxy.phonesin.member.model.entity.Member;
import com.regalaxy.phonesin.member.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    public ResponseEntity<Member> signUp(MemberDto memberDto) {
        if (memberRepository.existsByEmail(memberDto.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
        String encodedPassword = passwordEncoder.encode(memberDto.getPassword());

        Member member = Member.builder()
                .email(memberDto.getEmail())
                .password(encodedPassword)
                .memberName(memberDto.getMemberName())
                .phoneNumber(memberDto.getPhoneNumber())
                .isCha(memberDto.getIsCha())
                .isBlackList(memberDto.getIsBlackList())
                .isDelete(memberDto.getIsDelete())
                .isManager(memberDto.getIsManager())
                .build();

        Member savedMember = memberRepository.save(member);
        return ResponseEntity.ok(savedMember);
    }

    public ResponseEntity<String> signIn(LoginRequestDto loginRequestDto) {
        Optional<Member> memberOptional = memberRepository.findByEmail(loginRequestDto.getEmail());

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            if (passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
                return ResponseEntity.ok("로그인에 성공하였습니다.");
            } else {
                return ResponseEntity.status(401).body("비밀번호가 일치하지 않습니다.");
            }
        } else {
            return ResponseEntity.status(404).body("사용자를 찾을 수 없습니다.");
        }
    }
}