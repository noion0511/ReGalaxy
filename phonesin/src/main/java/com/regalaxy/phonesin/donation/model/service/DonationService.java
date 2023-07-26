package com.regalaxy.phonesin.donation.model.service;

import com.regalaxy.phonesin.donation.model.dto.DonationDto;
import com.regalaxy.phonesin.donation.model.dto.DonationListDto;
import com.regalaxy.phonesin.donation.model.repository.DonationRepository;
import com.regalaxy.phonesin.member.model.entity.Member;
import com.regalaxy.phonesin.member.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DonationService {
    private final DonationRepository donationRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public boolean donationApply(DonationDto donationDto) throws Exception{
        Member member = memberRepository.findById(donationDto.getMember_id()).get();
        donationRepository.save(donationDto.toEntity(member));
        return true;
    }

    @Transactional
    public List<DonationListDto> donationList() {
        List<DonationListDto> result = donationRepository.findAll()
                .stream()
                .map(Object -> DonationListDto.builder().donation(Object).build())
                .collect(Collectors.toList());
        return result;
    }
}
