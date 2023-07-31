package com.regalaxy.phonesin.donation.model.repository;

import com.regalaxy.phonesin.donation.model.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
}
