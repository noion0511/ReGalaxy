package com.regalaxy.phonesin.rental.model.service;

import com.regalaxy.phonesin.address.model.repository.AgencyRepository;
import com.regalaxy.phonesin.member.model.entity.Member;
import com.regalaxy.phonesin.member.model.repository.MemberRepository;
import com.regalaxy.phonesin.phone.model.entity.Phone;
import com.regalaxy.phonesin.phone.model.repository.PhoneRepository;
import com.regalaxy.phonesin.rental.model.*;
import com.regalaxy.phonesin.rental.model.entity.Rental;
import com.regalaxy.phonesin.rental.model.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AgencyRepository agencyRepository;

    public boolean infoApply(RentalApplyDto rentalApplyDto, Long memberId){
        for(int i=0; i<rentalApplyDto.getCount(); i++) {//갯수
            Rental rental = new Rental();
            rental.setUsingDate(rentalApplyDto.getUsingDate());
            Member member = memberRepository.findById(memberId).get();
            rental.setMember(member);//사용자 정보
            rental.setApplyDate(LocalDateTime.now());//신청 날짜
            rental.setRentalStatus(1);//배송 상태 : 1 = 신청 대기
            rental.setY2K(rentalApplyDto.isY2K());
            rental.setHomecam(rentalApplyDto.isHomecam());
            rental.setClimateHumidity(rentalApplyDto.isClimateHumidity());
            rental.setRentalDeliveryLocation(rentalApplyDto.getRentalDeliveryLocation());//배송지
            rental.setFund(rentalApplyDto.getFund());//가격
            rental.setUsingDate(rentalApplyDto.getUsingDate());//사용 기간
            rentalRepository.save(rental);
        }
        return true;
    }

    public boolean infoUpdated(RentalApplyDto rentalApplyDto, Long rental_id){
        Rental rental = rentalRepository.findById(rental_id).get();
        rental.setFund(rentalApplyDto.getFund());//가격
        rental.setHomecam(rentalApplyDto.isHomecam());
        rental.setClimateHumidity(rentalApplyDto.isClimateHumidity());
        rental.setY2K(rentalApplyDto.isY2K());
        rental.setRentalDeliveryLocation(rentalApplyDto.getRentalDeliveryLocation());//배달 주소
        rental.setUsingDate(rentalApplyDto.getUsingDate());//사용 기간
        rentalRepository.save(rental);
        return true;
    }

    public void infoDelete(Long rental_id){
        rentalRepository.deleteById(rental_id);
    }

    public List<RentalDto> infoList(RentalSearchDto rentalSearchDto){
        return rentalRepository.search(rentalSearchDto);
    }

    public List<RentalDto> clientInfoList(Long member_id){
        return rentalRepository.searchById(member_id);
    }

    public RentalDetailDto info(Long return_id){
        return rentalRepository.detailInfo(return_id);
    }

    public boolean extension(Long rental_id){
        Rental rental = rentalRepository.findById(rental_id).get();
        if(rental.isExtension()){//연장한 적 있다면
            return false;
        }else{
            rental.extension();//isExtension을 true로 변경
            int month = rental.getRentalEnd().getMonthValue() + 6;//6개월 연장
            int year = rental.getRentalEnd().getYear();
            if(month>12){
                rental.setRentalEnd(rental.getRentalEnd().withMonth(month-12).withYear(year+1));
            }else{
                rental.setRentalEnd(rental.getRentalEnd().withMonth(month));
            }
            rentalRepository.save(rental);
            return true;
        }
    }

    public List<RentalDto> apply(AdminRentalApplyDto adminRentalApplyDto){
        Rental rental = rentalRepository.findById(adminRentalApplyDto.getRentalId()).get();
        if(adminRentalApplyDto.isApply()) {//허락
            List<Phone> list = phoneRepository.findAll();
            for(Phone phone : list){
                if(!phone.isRental()) return null;
                if(rental.isY2K() && !phone.isY2K()) return null;
                if(rental.isHomecam() && !phone.isHomecam()) return null;
                if(rental.isClimateHumidity() && !phone.isClimateHumidity()) return null;
                break;
            }
            rental.setRentalStart(LocalDateTime.now());//대여 시작일
            int month = LocalDateTime.now().getMonthValue() + rental.getUsingDate();
            int year = LocalDateTime.now().getYear();
            if (month > 12) {//대여 끝나는 날짜
                rental.setRentalEnd(LocalDateTime.now().withMonth(month - 12).withYear(year + 1));
            } else {
                rental.setRentalEnd(LocalDateTime.now().withMonth(month));
            }
            rental.setRentalStatus(2);
            rentalRepository.save(rental);
        }
        else {//반려
            rental.setRentalStatus(-1);
            rentalRepository.save(rental);
        }
        RentalSearchDto rentalSearchDto = new RentalSearchDto();
        rentalSearchDto.setReady(adminRentalApplyDto.isReady());
        return rentalRepository.search(rentalSearchDto);
    }

    public int count(Long member_id){ // status 1, 2, 3
        return rentalRepository.countByMember_MemberIdAndRentalStatusLessThan(member_id, 4);
    }
}
