package com.regalaxy.phonesin.back.model.service;

import com.regalaxy.phonesin.back.model.BackDto;
import com.regalaxy.phonesin.back.model.entity.Back;
import com.regalaxy.phonesin.back.model.repository.BackRepository;
import com.regalaxy.phonesin.rental.model.entity.Rental;
import com.regalaxy.phonesin.rental.model.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BackService {

    private final BackRepository backRepository;
    private final RentalRepository rentalRepository;

    // 반납 신청서 저장하기
    @Transactional
    public void saveBack(BackDto backdto) {
        Rental rental = rentalRepository.findById(backdto.getRentalId()).get();
        backRepository.save(backdto.toEntity(rental));
    }

    // backId인 반납 신청서 read
    public Back findOne(Long backId) {
        return backRepository.findById(backId).get();
    }
}
