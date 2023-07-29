package com.regalaxy.phonesin.donation.model.service;

import com.regalaxy.phonesin.donation.model.dto.DonationDetailResponseDto;
import com.regalaxy.phonesin.donation.model.dto.DonationRequestDto;
import com.regalaxy.phonesin.donation.model.dto.DonationResponseDto;
import com.regalaxy.phonesin.donation.model.entity.Donation;
import com.regalaxy.phonesin.donation.model.repository.DonationRepository;
import com.regalaxy.phonesin.member.model.entity.Member;
import com.regalaxy.phonesin.member.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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
    public boolean donationApply(DonationRequestDto donationRequestDto) throws Exception {
        Member member = memberRepository.findById(donationRequestDto.getMember_id()).get();
        donationRepository.save(donationRequestDto.toEntity(member));
        return true;
    }

    public List<DonationResponseDto> donationList() throws Exception {
        List<DonationResponseDto> result = donationRepository.findAll()
                .stream()
                .map(Object -> DonationResponseDto.builder().donation(Object).build())
                .collect(Collectors.toList());
        return result;
    }

    public DonationDetailResponseDto donationInfo(Long id) throws Exception {
        Donation donation = donationRepository.findById(id).get();
        Member member = donation.getMember();
        DonationDetailResponseDto result = DonationDetailResponseDto.builder().donation(donation).member(member).build();
        return result;
    }

    @Transactional
    public boolean donationUpdate(DonationRequestDto donationRequestDto) throws Exception {
        Optional<Donation> optional = donationRepository.findById(donationRequestDto.getDonation_id());
        if (optional.isPresent()) {
            Member member = memberRepository.findById(donationRequestDto.getMember_id()).get();
            Donation donation = optional.get();
            donation.setMember(member);
            donation.setDonation_status(donationRequestDto.getDonation_status());
            donation.setDonation_delivery_location(donationRequestDto.getDonation_delivery_location());
            donation.setDonation_delivery_location_type(donationRequestDto.getDonation_delivery_location_type());
            donation.setDonation_delivery_date(donationRequestDto.getDonation_delivery_date());
            return true;
        }
        return false;
    }

    @Transactional
    public boolean donationDelete(Long donation_id) throws Exception {
        donationRepository.deleteById(donation_id);
        return true;
    }

    public List<DonationResponseDto> donationKing() throws Exception {
        List<DonationResponseDto> result = donationRepository.findAll()
                .stream()
                .map(Object -> DonationResponseDto.builder().donation(Object).build())
                .collect(Collectors.toList());
        return result;
    }
}
