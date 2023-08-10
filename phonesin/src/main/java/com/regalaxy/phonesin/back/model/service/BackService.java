package com.regalaxy.phonesin.back.model.service;

import com.regalaxy.phonesin.back.model.*;
import com.regalaxy.phonesin.back.model.entity.Back;
import com.regalaxy.phonesin.back.model.repository.BackRepository;
import com.regalaxy.phonesin.member.model.jwt.JwtTokenProvider;
import com.regalaxy.phonesin.phone.model.repository.PhoneRepository;
import com.regalaxy.phonesin.rental.model.entity.Rental;
import com.regalaxy.phonesin.rental.model.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BackService {

    private final BackRepository backRepository;
    private final RentalRepository rentalRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 반납 신청서 저장하기
    @Transactional
    public void apply(BackDto backdto) {
        Rental rental = rentalRepository.findById(backdto.getRentalId()).get();
        backRepository.save(backdto.toEntity(rental));
    }

    // backId인 반납 신청서 read
    @Transactional
    public BackInfoDto backInfo(Long backId, String token) {
        Back back = backRepository.findById(backId).get();
        if (back.getRental().getMember().getMemberId() != jwtTokenProvider.getMemberId(token)) {
            throw new IllegalArgumentException("해당 유저가 아닙니다.");
        };
        return new BackInfoDto(back);
    }

    // 전체 반납 신청서 조회/검색/페이징
    @Transactional
    public Page<Back> backList(String email, int isBlack, int isCha, Pageable pageable) {
        Page<Back> findall;
        boolean isBlackBoolean;
        boolean isChaBoolean;

//         email, isBlack, isCha 각각으로 검색할 수 있도록 개발 (셋 중에 하나만 됨)
//         isBlack, isCha : 모두(1), Black 또는 Cha가 맞다(2), 아니다(3)
//
        // email에 아무것도 입력하지 않은 상태면 모든 반납 신청서 return
        if (email == null) {
            // 1이면 모두 검색이므로 조건문 넣지 않는다.
            if (isBlack == 1){
                if (isCha == 1) {
                    findall = backRepository.findAll(pageable);
                } else {
                    // isBlack이 2, 3이면 Math.abs(isBlack - 3)은 1, 0
                    isBlackBoolean = (Math.abs(isCha - 3) == 1) ? true : false;
                    findall = backRepository.findAllByIsCha(isBlackBoolean, pageable);
                }
            } else {
                // isCha가 2, 3이면 Math.abs(isCha - 3)은 1, 0
                isChaBoolean = (Math.abs(isBlack - 3) == 1) ? true : false;
                findall = backRepository.findAllByIsBlack(isChaBoolean, pageable);
            }
        } else {
            findall = backRepository.findAllByEmail(email, pageable);
        }

        return findall;
    }

    // 관리자가 반납 신청서 수정
    @Transactional
    public BackDto updateBackByAdmin(BackDto backDto) {
        // DB에 없는 ID를 검색하려고 하면 IllegalArgumentException
        Back back = backRepository.findById(backDto.getBackId())
                .orElseThrow(() -> new IllegalArgumentException(backDto.getBackId() + "인 ID는 존재하지 않습니다."));
        back.updateByAdmin(backDto);
        backRepository.save(back);
        return BackDto.fromEntity(back);
    }

    // 사용자가 자신의 반납 신청서 수정
    @Transactional
    public BackDto updateBackByUser(BackUserDto backUserDto, String authorization) {
        // DB에 없는 ID를 검색하려고 하면 IllegalArgumentException
        Long memberId = jwtTokenProvider.getMemberId(authorization.replace("Bearer ", ""));
        Back back = backRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException(memberId + "인 ID는 존재하지 않습니다."));
        if (backUserDto.getMemberId(back) != memberId) {
            throw new IllegalArgumentException("현재 계정의 멤버 아이디가" + backUserDto.getMemberId(back) + "이 아닙니다.");
        };
        back.updateByUser(backUserDto);
        backRepository.save(back);
        return BackDto.fromEntity(back);
    }

    // 반납 리스트 조회
    public List<BackAdminDto> list(BackAdminSearschDto backAdminSearschDto ){
        List<Back> list = backRepository.findAll();
        List<BackAdminDto> backAdminDtos = new ArrayList<>();
        for(Back back : list){
            if(backAdminSearschDto.isSuccess() && back.getBackStatus()==1) continue;
            BackAdminDto backAdminDto = new BackAdminDto();
            backAdminDto.setBackStatus(back.getBackStatus());
            backAdminDto.setReview(back.getReview());
            backAdminDto.setBackId(back.getBackId());
            backAdminDto.setCreateAt(back.getCreatedAt());
            backAdminDtos.add(backAdminDto);
        }
        return backAdminDtos;
    }
}