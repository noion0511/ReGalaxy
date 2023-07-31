package com.regalaxy.phonesin.member.model.service;

import com.regalaxy.phonesin.member.model.MemberDto;
import com.regalaxy.phonesin.member.model.entity.Member;
import com.regalaxy.phonesin.member.model.repository.MemberRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    public MemberDto signUp(MemberDto memberDto) {
        Member member = convertToEntity(memberDto);
        Member savedMember = memberRepository.save(member);
        return convertToDto(savedMember);
    }

    private Member convertToEntity(MemberDto memberDto) {
        return Member.builder()
                .email(memberDto.getEmail())
                .memberName(memberDto.getMemberName())
                .password(memberDto.getPassword())
                .phoneNumber(memberDto.getPhoneNumber())
                .isCha(memberDto.getIsCha())
                .isBlackList(memberDto.getIsBlackList())
                .isDelete(memberDto.getIsDelete())
                .isManager(memberDto.getIsManager())
                .build();
    }

    private MemberDto convertToDto(Member member) {
        return MemberDto.builder()
                .email(member.getEmail())
                .memberName(member.getMemberName())
                .password(member.getPassword())
                .phoneNumber(member.getPhoneNumber())
                .isCha(member.getIsCha())
                .isBlackList(member.getIsBlackList())
                .isDelete(member.getIsDelete())
                .isManager(member.getIsManager())
                .build();
    }

}