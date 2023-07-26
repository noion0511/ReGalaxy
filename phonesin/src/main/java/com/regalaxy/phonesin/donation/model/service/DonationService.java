package com.regalaxy.phonesin.donation.model.service;

import com.regalaxy.phonesin.donation.model.entity.Donation;
import com.regalaxy.phonesin.donation.model.repository.DonationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DonationService {
    private final DonationRepository donationRepository;

    @Transactional
    public boolean donationApply(Donation donation) {
        donationRepository.save(donation);
        return true;
    }

    public List<Donation> donationList() {
        return donationRepository.findAll();
    }
}
