package com.regalaxy.phonesin.rental.model.repository;

import com.regalaxy.phonesin.donation.model.entity.Donation;
import com.regalaxy.phonesin.rental.model.RentalDto;
import com.regalaxy.phonesin.rental.model.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long>, RentalRepositoryCustom {
    List<Rental> findAllByMember_MemberId(Long memberId);
    int countByMember_MemberId(Long memberId);
}
