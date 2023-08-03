package com.regalaxy.phonesin.member.model.service;

import com.regalaxy.phonesin.member.model.MemberAdminDto;
import com.regalaxy.phonesin.member.model.MemberUserDto;
import com.regalaxy.phonesin.member.model.LoginRequestDto;
import com.regalaxy.phonesin.member.model.MemberDto;
import com.regalaxy.phonesin.member.model.entity.Member;
import com.regalaxy.phonesin.member.model.jwt.JwtTokenProvider;
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
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    public ResponseEntity<Member> signUp(MemberDto memberDto) {
        if (memberRepository.existsByEmail(memberDto.getEmail())) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        // 비밀번호 암호화
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
                .createdAt(memberDto.getCreatedAt())
                .build();

        Member savedMember = memberRepository.save(member);
        return ResponseEntity.ok(savedMember);
    }

    // 로그인 및 토큰 발급
    public ResponseEntity<String> signIn(LoginRequestDto loginRequestDto, String refreshToken) {
        Optional<Member> memberOptional = memberRepository.findByEmail(loginRequestDto.getEmail());
        memberOptional.get().updateRefreshToken(refreshToken);
        memberRepository.save(memberOptional.get());

        if (!memberOptional.get().getIsDelete()) {
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
        } else {
            return ResponseEntity.status(404).body("탈퇴한 유저입니다.");
        }
    }

    // 리프레시 토큰을 DB에 저장하는 서비스
    public void saveRefreshToken(String email, String token) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        member.updateRefreshToken(token);
        memberRepository.save(member);
    }

    public MemberUserDto UserInfo(Long memberId) {
        return Member.toUserDto(memberRepository.findById(memberId).get());
    }

    public MemberAdminDto AdminInfo(String email) {
        return Member.toAdminDto(memberRepository.findByEmail(email).get());
    }

    // 회원 정보 수정
    public MemberDto updateMemberByAdmin(MemberAdminDto memberAdminDto) {
        // DB에 없는 ID를 검색하려고 하면 IllegalArgumentException
        Member member = memberRepository.findById(memberAdminDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException(memberAdminDto.getMemberId() + "인 ID는 존재하지 않습니다."));
        member.updateByAdmin(memberAdminDto);
        memberRepository.save(member);
        return MemberDto.fromEntity(member);
    }

    public MemberDto updateMemberByUser(MemberUserDto memberUserDto) {
        // DB에 없는 ID를 검색하려고 하면 IllegalArgumentException
        Member member = memberRepository.findByEmail(memberUserDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(memberUserDto.getEmail() + "은 존재하지 않습니다."));
        member.updateByUser(memberUserDto);
        memberRepository.save(member);
        return MemberDto.fromEntity(member);
    }

    // 회원 탈퇴
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException(memberId + "인 ID는 존재하지 않습니다."));
        member.delete();
        memberRepository.save(member);
    }

    public Object UserInfoByAdmin(Long memberId) {
        return Member.toAdminDto(memberRepository.findById(memberId).get());
    }
}