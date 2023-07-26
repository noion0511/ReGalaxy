package com.regalaxy.phonesin.donation.model.service;
import com.regalaxy.phonesin.donation.model.DonationDto;
import com.regalaxy.phonesin.donation.model.repository.DonationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DonationService {
    private final Logger logger = LoggerFactory.getLogger(DonationService.class);

    private final DonationRepository donationRepository;

    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public boolean donationApply(DonationDto donationDto){
        donationRepository.save(donationDto);
        return true;
    }
}
