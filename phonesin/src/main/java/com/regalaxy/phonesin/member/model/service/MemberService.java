package com.regalaxy.phonesin.member.model.service;

import com.regalaxy.phonesin.member.model.*;
import com.regalaxy.phonesin.member.model.entity.Member;
import com.regalaxy.phonesin.member.model.jwt.JwtTokenProvider;
import com.regalaxy.phonesin.member.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final JavaMailSender javaMailSender;
    private final Random random = new SecureRandom();

    // 회원가입
    public ResponseEntity<Member> signUp(MemberSignUpDto memberSignUpDto) {
        if (memberRepository.existsByEmail(memberSignUpDto.getEmail()) && !memberRepository.findByEmail(memberSignUpDto.getEmail()).get().getIsDelete()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        } else {
            // 비밀번호 암호화
            String encodedPassword = passwordEncoder.encode(memberSignUpDto.getPassword());

            Member member;
            if (!memberRepository.existsByEmail(memberSignUpDto.getEmail())) {
                member = new Member();
                member.update(memberSignUpDto, encodedPassword);

                Member savedMember = memberRepository.save(member);
            } else {
                // 삭제한 이메일로 다시 한 번 회원가입 할 때
                member = memberRepository.findByEmail(memberSignUpDto.getEmail()).get();

                member.update(memberSignUpDto, encodedPassword);

                Member savedMember = memberRepository.save(member);
            }

            return ResponseEntity.ok(member);
        }
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

    // 사용자가 자신의 정보를 조회하는 서비스
    public MemberUserDto UserInfo(Long memberId) {
        return Member.toUserDto(memberRepository.findById(memberId).get());
    }

    // 관리자가 회원의 정보를 조회하는 서비스(ID)
    public MemberAdminDto UserInfoByAdmin(Long memberId) {
        return Member.toAdminDto(memberRepository.findById(memberId).get());
    }

    // 관리자가 회원의 정보를 조회하는 서비스(Email)
    public MemberAdminDto AdminInfo(String email) {
        return Member.toAdminDto(memberRepository.findByEmail(email).get());
    }

    // 관리자가 회원의 정보를 수정하는 서비스
    public MemberDto updateMemberByAdmin(MemberAdminDto memberAdminDto) {
        // DB에 없는 ID를 검색하려고 하면 IllegalArgumentException
        Member member = memberRepository.findById(memberAdminDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException(memberAdminDto.getMemberId() + "인 ID는 존재하지 않습니다."));
        member.updateByAdmin(memberAdminDto);
        memberRepository.save(member);
        return MemberDto.fromEntity(member);
    }

    // 사용자가 자신의 정보를 수정하는 서비스
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

    // 비밀번호 변경
    public void changePassword(MemberUpdatePasswordDto requestDto) {
        Member member = memberRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        // 기존 비밀번호 안맞으면 Exception
        if (!passwordEncoder.matches(requestDto.getOldPassword(), member.getPassword())) {
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }

        member.updatePassword(passwordEncoder.encode(requestDto.getNewPassword()));
        memberRepository.save(member);
    }

    // 이메일 인증
    public void requestEmailVerification(String email) {
        String code = generateVerificationCode();

        // 이메일이 이미 DB에 존재하는 경우
        if (memberRepository.existsByEmail(email)) {
            Member existingMember = memberRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

            // 해당 이메일에 대한 사용자 계정이 삭제 상태가 아니라면 에러 메시지 반환
            if (!existingMember.getIsDelete()) {
                throw new RuntimeException("이미 존재하는 이메일입니다.");
            } else {
                // 삭제된 사용자라면 인증 코드를 업데이트
                existingMember.setVerificationCode(email, code);
                memberRepository.save(existingMember);
            }
        } else {
            // 이메일이 DB에 없는 경우 새로운 Member 객체 생성 및 저장
            Member newMember = new Member();
            newMember.setVerificationCode(email, code);
            memberRepository.save(newMember);
        }

        String message = "다음 코드를 입력하여 이메일을 확인해주세요: " + code;
        sendEmail(email, "이메일 확인 코드", message);
    }

    // 이메일 인증코드 확인
    public void confirmEmailVerification(String email, String code) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));

        if (!member.getVerificationCode().equals(code)) {
            throw new RuntimeException("인증 코드가 일치하지 않습니다.");
        }

        member.setVerified(true); // 이메일이 인증되었음을 표시
        memberRepository.save(member);
    }

    // 인증코드 발급
    private String generateVerificationCode() {
        int code = 100000 + random.nextInt(900000); // 100000에서 999999 사이의 난수 생성
        return Integer.toString(code);
    }

    // 이메일 보내기 (JavaMailSender 사용)
    public void sendEmail(String to, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
    }

    // 멤버 리스트 조회
    public List<MemberDto> list(MemberSearchDto memberSearchDto){
        List<Member> member = memberRepository.findAll();
        List<MemberDto> memberDtos = new ArrayList<>();
        for(Member m : member){
            if(m.getIsManager()) continue;
            if(memberSearchDto.isBlack() && !m.getIsBlackList()) continue;
            if(memberSearchDto.isCha() && !m.getIsCha()) continue;
            MemberDto memberDto = new MemberDto();
            memberDto.setEmail(m.getEmail());
            memberDto.setMemberName(m.getMemberName());
            memberDto.setIsBlackList(m.getIsBlackList());
            memberDto.setIsCha(m.getIsCha());
            memberDto.setCreatedAt(m.getCreatedAt());
            memberDto.setPhoneNumber(m.getPhoneNumber());
            memberDtos.add(memberDto);
        }
        return memberDtos;
    }

    // 회원 블랙리스트 설정하기
    public void blackListMember(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일이 " + email + "인 사용자는 존재하지 않습니다."));
        member.setBlackList();
        memberRepository.save(member);
    }
}