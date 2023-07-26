package com.regalaxy.phonesin.donation.model.service;

import com.regalaxy.phonesin.donation.model.dto.DonationDto;
import com.regalaxy.phonesin.donation.model.dto.DonationListDto;
import com.regalaxy.phonesin.donation.model.entity.Donation;
import com.regalaxy.phonesin.donation.model.repository.DonationRepository;
import com.regalaxy.phonesin.member.model.entity.Member;
import com.regalaxy.phonesin.member.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DonationService {
    private final DonationRepository donationRepository;
    private final MemberRepository memberRepository;
    private final EntityManager em;

    @Transactional
    public boolean donationApply(DonationDto donationDto) throws Exception {
        Member member = memberRepository.findById(donationDto.getMember_id()).get();
        donationRepository.save(donationDto.toEntity(member));
        return true;
    }

    public List<DonationListDto> donationList() throws Exception {
        List<DonationListDto> result = donationRepository.findAll()
                .stream()
                .map(Object -> DonationListDto.builder().donation(Object).build())
                .collect(Collectors.toList());
        return result;
    }

    public DonationListDto donationInfo(Long id) throws Exception {
        DonationListDto result = DonationListDto.builder().donation(donationRepository.findById(id).get()).build();
        return result;
    }

    @Transactional
    public boolean donationUpdate(DonationDto donationDto) throws Exception {
        if (donationRepository.findById(donationDto.getDonation_id()).isPresent()) {
            Member member = memberRepository.findById(donationDto.getMember_id()).get();
            donationRepository.save(donationDto.toEntity(member));
            return true;
        }
        return false;
    }

    @Transactional
    public boolean donationDelete(Long donation_id) throws Exception {
        donationRepository.deleteById(donation_id);
        return true;
    }

    public List<DonationListDto> donationKing() throws Exception {
        List<DonationListDto> result = donationRepository.findTop5ByOrderById()
                .stream()
                .map(Object -> DonationListDto.builder().donation(Object).build())
                .collect(Collectors.toList());
        return result;
    }
}
