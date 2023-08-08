package com.regalaxy.phonesin.phone.model.repository;

import com.regalaxy.phonesin.phone.model.PhoneDto;
import com.regalaxy.phonesin.phone.model.PhoneSearchDto;
import com.regalaxy.phonesin.phone.model.entity.Phone;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PhoneRepositoryCustomImpl implements PhoneRepositoryCustom{

    @Autowired
    private final EntityManager em;

    @Override
    public List<PhoneDto> search(PhoneSearchDto phoneSearchDto) {
        String s = "select new com.regalaxy.phonesin.phone.model.PhoneDto(p.phoneId, p.serialNumber, m.modelName, m.nickname, p.rental.rentalId, p.isY2K, p.isClimateHumidity, p.isHomecam) "
                + "from phone p join model m on p.model.modelId = m.modelId ";
        int n = 0;
        if(phoneSearchDto.isRental()){//빌려진 폰
            if(n==0){
                s+="where ";
            }
            s += "p.rental.rentalId is not null ";//서브쿼리로 member_id 찾기
            n++;
        }
        if(phoneSearchDto.isY2K()){//가능
            if(n==0){
                s+=" where ";
            }
            if(n>0){
                s+= " and ";
            }
            s+= "p.isY2K = true ";
            n++;
        }
        if(phoneSearchDto.isHomecam()){//가능
            if(n==0){
                s+=" where ";
            }
            if(n>0){
                s+= " and ";
            }
            s+= "p.isHomecam = true ";
            n++;
        }
        if(phoneSearchDto.isClimateHumidity()){//가능
            if(n==0){
                s+=" where ";
            }
            if(n>0){
                s+= " and ";
            }
            s+= "p.isClimateHumidity = true ";
            n++;
        }

        return em.createQuery(s, PhoneDto.class).getResultList();
    }
}
