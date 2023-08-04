package com.regalaxy.phonesin.donation.model.service;

import com.regalaxy.phonesin.donation.model.DonationDetailResponseDto;
import com.regalaxy.phonesin.donation.model.DonationKingDto;
import com.regalaxy.phonesin.donation.model.DonationRequestDto;
import com.regalaxy.phonesin.donation.model.DonationResponseDto;
import com.regalaxy.phonesin.donation.model.entity.Donation;
import com.regalaxy.phonesin.donation.model.repository.DonationRepository;
import com.regalaxy.phonesin.member.model.entity.Member;
import com.regalaxy.phonesin.member.model.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DonationService {
    private final DonationRepository donationRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public boolean donationApply(DonationRequestDto donationRequestDto) throws Exception {
        Member member = memberRepository.findById(1l).get();
        donationRepository.save(donationRequestDto.toEntity(member));
        return true;
    }

    public List<DonationResponseDto> donationList() throws Exception {
        List<DonationResponseDto> result = donationRepository.findAll()
                .stream()
                .map(donation -> new DonationResponseDto(donation))
                .collect(Collectors.toList());
        return result;
    }

    public DonationDetailResponseDto donationInfo(Long donationId) throws Exception {
        Donation donation = donationRepository.findById(donationId).get();
        DonationDetailResponseDto result = new DonationDetailResponseDto(donation);

        return result;
    }

    @Transactional
    public boolean donationUpdate(DonationRequestDto donationRequestDto) throws Exception {
        Optional<Donation> optional = donationRepository.findById(1l);
        if (optional.isPresent()) {
            Member member = memberRepository.findById(1l).get();
            Donation donation = optional.get();
            donation.setMember(member);
            donation.setDonationStatus(donationRequestDto.getDonationStatus());
            donation.setDonationDeliveryLocation(donationRequestDto.getDonationDeliveryLocation());
            donation.setDonationDeliveryLocationType(donationRequestDto.getDonationDeliveryLocationType());
            donation.setDonationDeliveryDate(donationRequestDto.getDonationDeliveryDate());
            return true;
        }
        return false;
    }

    @Transactional
    public boolean donationDelete(Long donationId) throws Exception {
        donationRepository.deleteById(donationId);
        return true;
    }

    public List<DonationKingDto> donationKing() throws Exception {
        List<DonationKingDto> kingDtoList = donationRepository.findDonationKing(LocalDateTime.now().withDayOfMonth(1), LocalDateTime.now());
        if (kingDtoList.size() > 5){
            kingDtoList = kingDtoList.subList(0, 5);
        }
        return kingDtoList;
    }

    public List<DonationResponseDto> donationlist(Long memberId) throws Exception {
        List<DonationResponseDto> result = donationRepository.findAllByMember_MemberId(memberId)
                .stream()
                .map(donation -> new DonationResponseDto(donation))
                .collect(Collectors.toList());
        return result;
    }
}
