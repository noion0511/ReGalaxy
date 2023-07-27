package com.regalaxy.phonesin.donation.model.repository;

import com.regalaxy.phonesin.donation.model.entity.Donation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DonationRepository extends JpaRepository<Donation, Long> {
}
