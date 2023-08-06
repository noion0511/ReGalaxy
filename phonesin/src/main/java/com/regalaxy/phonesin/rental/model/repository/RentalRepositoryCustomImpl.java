package com.regalaxy.phonesin.rental.model.repository;

import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.rental.model.RentalDetailDto;
import com.regalaxy.phonesin.rental.model.RentalDto;
import com.regalaxy.phonesin.rental.model.RentalSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RentalRepositoryCustomImpl implements RentalRepositoryCustom {

    @Autowired
    private final EntityManager em;
    @Override
    public List<RentalDto> search(RentalSearchDto rentalSearchDto) {
        String s = "select new com.regalaxy.phonesin.rental.model.RentalDto(r.rentalId, r.applyDate, r.rentalStart, r.rentalEnd, r.rentalStatus, r.rentalDeliveryLocation, r.fund, m.modelName, p.phoneId, r.waybillNumber, r.isY2K, r.isClimateHumidity, r.isHomecam) "
            + "from rental r left join phone p on r.rentalId = p.rental.rentalId "
                + "left join model m on p.model.modelId = m.modelId ";
        int n = 0;
        if(rentalSearchDto.isReady()){//이메일 검색을 했을 경우
            if(n==0){
                s+="where ";
            }
            s += "r.rentalStatus=1 ";
            n++;
        }

        System.out.println(s);

        return em.createQuery(s, RentalDto.class)
                .getResultList();
    }

    @Override
    public List<RentalDto> searchById(Long member_id) {
        String s = "select new com.regalaxy.phonesin.rental.model.RentalDto(r.rentalId, r.applyDate, r.rentalStart, r.rentalEnd, r.rentalStatus, r.rentalDeliveryLocation, r.fund, m.modelName, p.phoneId, r.waybillNumber, r.isY2K, r.isClimateHumidity, r.isHomecam) "
                + "from rental r left join phone p on r.rentalId = p.rentalId "
                + "left join model m on p.model.modelId = m.modelId "
                + "where r.member.memberId=" + member_id + " and p.rentalId is null and p.model.modelId is null";

        return em.createQuery(s, RentalDto.class).getResultList();
    }

    @Override
    public RentalDetailDto detailInfo(Long rental_id) {
        String s = "select new com.regalaxy.phonesin.rental.model.RentalDetailDto(r.rentalId, r.member.memberId, r.isY2K, r.isClimateHumidity, r.isHomecam, r.count, r.rentalStart, r.rentalEnd, r.applyDate, r.rentalStatus, r.rentalDeliveryLocation, r.fund, m.modelName, p.phoneId, p.donation.donationId, r.usingDate) "
            + "from rental r left join phone p on r.rentalId = p.rentalId "
            + "left join model m on p.model.modelId = m.modelId "
                + "where r.rentalId=" + rental_id;

        List<RentalDetailDto> list = em.createQuery(s, RentalDetailDto.class).getResultList();
        if(list.size()!=0){
            return list.get(0);
        }else{
            return new RentalDetailDto();
        }
    }
}
