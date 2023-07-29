package com.regalaxy.phonesin.back.model.service;

import com.regalaxy.phonesin.back.model.BackDto;
import com.regalaxy.phonesin.back.model.entity.Back;
import com.regalaxy.phonesin.back.model.repository.BackRepository;
import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.rental.model.entity.Rental;
import com.regalaxy.phonesin.rental.model.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BackService {

    private final BackRepository backRepository;
    private final RentalRepository rentalRepository;

    // 반납 신청서 저장하기
    @Transactional
    public void apply(BackDto backdto) {
        Rental rental = rentalRepository.findById(backdto.getRentalId()).get();
        backRepository.save(backdto.toEntity(rental));
    }

    // backId인 반납 신청서 read
    @Transactional
    public Back backInfo(Long backId) {
        return backRepository.findById(backId).get();
    }

    // 전체 반납 신청서 조회/검색/페이징
    @Transactional
    public Page<Back> backList(String email, int isBlack, int isCha, Pageable pageable) {
        Page<Back> findall;
        boolean isBlackBoolean;
        boolean isChaBoolean;

        // email로 검색하거나, isBlack으로 검색하거나, isCha로 검색할 수 있도록 개발 (셋 중에 하나만 됨)
        // isBlack, isCha : 모두(1), Black 또는 Cha가 맞다(2), 아니다(3)

        // email에 아무것도 입력하지 않은 상태면 모든 반납 신청서 return
        if (email == null) {
            // 1이면 모두 검색이므로 조건문 넣지 않는다.
            if (isBlack == 1) {
                // 1이면 모두 검색이므로 조건문 넣지 않는다.
                if (isCha == 1) {
                    // 조건문 없이 모든 값 반환.
                    findall = backRepository.findAll(pageable);
                } else {
                    // isBlack이 2이면 Math.abs(isBlack - 3)은 1
                    // isBlack이 3이면 Math.abs(isBlack - 3)은 0
                    isBlackBoolean = (Math.abs(isCha - 3) == 1) ? true : false;
                    findall = backRepository.findAllByIsCha(isBlackBoolean, pageable);
                }
            } else {
                // isCha가 2이면 Math.abs(isCha - 3)은 1
                // isCha가 3이면 Math.abs(isCha - 3)은 0
                isChaBoolean = (Math.abs(isBlack - 3) == 1) ? true : false;
                findall = backRepository.findAllByIsBlack(isChaBoolean, pageable);
            }
        } else {
            // email이 null이 아니라면 email이 쓴 반납 신청서 return
            findall = backRepository.findAllByEmail(email, pageable);
        }

        return findall;
    }

    @Transactional
    public BackDto updateBack(BackDto backDto) {
        Back back = backRepository.findById(backDto.getBackId())
                .orElseThrow(() -> new IllegalArgumentException(backDto.getBackId() + "인 ID는 존재하지 않습니다."));
        back.update(backDto);
        backRepository.save(back);
        return BackDto.fromEntity(back);
    }
}
