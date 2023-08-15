package com.regalaxy.phonesin.rental.model.repository;

import com.regalaxy.phonesin.donation.model.entity.Donation;
import com.regalaxy.phonesin.rental.model.RentalDto;
import com.regalaxy.phonesin.rental.model.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long>, RentalRepositoryCustom {
    List<Rental> findAllByMember_MemberId(Long memberId);

    int countByMember_MemberIdAndRentalStatusLessThan(Long memberId, int rentalStatus);

    @Query("SELECT SUM(r.fund) FROM rental r")
    Double sumOfFunds();
}
