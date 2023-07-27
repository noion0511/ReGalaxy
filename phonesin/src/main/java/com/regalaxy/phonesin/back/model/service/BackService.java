package com.regalaxy.phonesin.back.model.service;

import com.regalaxy.phonesin.back.model.BackDto;
import com.regalaxy.phonesin.back.model.entity.Back;
import com.regalaxy.phonesin.back.model.repository.BackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BackService {

    private final BackRepository backRepository;

    @Transactional
    public void saveBack(Back back) {
        backRepository.save(back);
    }

    // backId인 반납 신청서 read
    public Back findOne(Long backId) {
        Back back = backRepository.findById(backId).get();
        back.setApplyDate(LocalDateTime.now());
        return back;
    }
}
