package com.regalaxy.phonesin.donation.model.repository;

import com.regalaxy.phonesin.donation.model.DonationDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<DonationDto, Long> {

}
