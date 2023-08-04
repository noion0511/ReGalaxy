package com.regalaxy.phonesin.donation.model.repository;

import com.regalaxy.phonesin.donation.model.DonationKingDto;
import com.regalaxy.phonesin.donation.model.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    @Query(value =
            "SELECT " +
                    "new com.regalaxy.phonesin.donation.model.DonationKingDto(d.member.memberName, COUNT(d)) " +
                    "FROM donation d " +
                    "where d.createdAt between :firstday and :lastday " +
                    "GROUP BY d.member.memberId " +
                    "order by count(d) DESC , d.member.memberName "
    )
    List<DonationKingDto> findDonationKing(@Param("firstday") LocalDateTime firstday, @Param("lastday") LocalDateTime lastday);
//    SELECT m.member_name, count(*) as donation_count FROM s09p12d102.donation as d join s09p12d102.member as m on d.member_id = m.member_id
//    group by d.member_id
//    order by donation_count DESC
//    limit 5;

    //"select new com.regalaxy.phonesin.rental.model.RentalDetailDto
    // (r.rentalId, r.member.memberId, r.isY2K, r.isClimateHumidity, r.isHomecam,
    // r.count, r.rentalStart, r.rentalEnd, r.applyDate, r.rentalStatus, r.rentalDeliveryLocation,
    // r.fund, m.modelName, p.phoneId, p.donation.donationId, r.usingDate) "
    //            + "from rental r join phone p on r.rentalId = p.rentalId "

    List<Donation> findAllByMember_MemberId(Long memberId);
}
