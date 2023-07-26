package com.regalaxy.phonesin.back.model.repository;

import com.regalaxy.phonesin.back.model.BackDto;
import com.regalaxy.phonesin.back.model.entity.Back;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class BackRepository {

    private final EntityManager em;

    public void save(BackDto backDto) {
        if (backDto.getBackId() == null) {
            em.persist(backDto);
        } else {
            em.merge(backDto);
        }
    }

    // back_id인 반납 신청서 read
    public BackDto findOne(Long back_id) {
        return em.find(BackDto.class, back_id);
    }
}
