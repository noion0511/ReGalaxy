package com.regalaxy.phonesin.phone.model.repository;

import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.phone.model.PhoneDto;
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
    public List<PhoneDto> search(SearchDto searchDto) {
        String s = "select new com.regalaxy.phonesin.phone.model.PhoneDto(p.phoneId, m.modelName, m.nickname, p.rentalId) "
                + "from phone p join model m on p.model.modelId = m.modelId ";
        int n = 0;
        if(searchDto.getIsRental()==2){//빌려진 폰
            if(n==0){
                s+=" where ";
            }
            s += "p.rentalId is not null";//서브쿼리로 member_id 찾기
            n++;
        }
        else if(searchDto.getIsRental()==3){//빌려지지 않은 폰
            if(n==0){
                s+=" where ";
            }
            s += "p.rentalId is null";//서브쿼리로 member_id 찾기
            n++;
        }
        if(searchDto.getIsY2K() == 2){//가능
            if(n==0){
                s+=" where ";
            }
            if(n>0){
                s+= " and ";
            }
            s+= "p.isY2K = true";
            n++;
        }
        else if(searchDto.getIsY2K() == 3){//불가능
            if(n==0){
                s+=" where ";
            }
            if(n>0){
                s+= " and ";
            }
            s+= "p.isY2K = false";
            n++;
        }
        if(searchDto.getIsHomecam() == 2){//가능
            if(n==0){
                s+=" where ";
            }
            if(n>0){
                s+= " and ";
            }
            s+= "p.isHomecam = true";
            n++;
        }
        else if(searchDto.getIsHomecam() == 3){//불가능
            if(n==0){
                s+=" where ";
            }
            if(n>0){
                s+= " and ";
            }
            s+= "p.isHomecam = false";
            n++;
        }
        if(searchDto.getIsClimateHumidity() == 2){//가능
            if(n==0){
                s+=" where ";
            }
            if(n>0){
                s+= " and ";
            }
            s+= "p.isClimateHumidity = true";
            n++;
        }
        else if(searchDto.getIsClimateHumidity() == 3){//불가능
            if(n==0){
                s+=" where ";
            }
            if(n>0){
                s+= " and ";
            }
            s+= "p.isClimateHumidity = false";
            n++;
        }

        return em.createQuery(s, PhoneDto.class)
                .getResultList();
    }
}
