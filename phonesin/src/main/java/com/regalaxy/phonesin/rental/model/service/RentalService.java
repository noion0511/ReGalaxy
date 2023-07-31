package com.regalaxy.phonesin.rental.model.service;

import com.regalaxy.phonesin.address.model.repository.AgencyRepository;
import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.member.model.entity.Member;
import com.regalaxy.phonesin.member.model.repository.MemberRepository;
import com.regalaxy.phonesin.phone.model.repository.PhoneRepository;
import com.regalaxy.phonesin.rental.model.RentalDetailDto;
import com.regalaxy.phonesin.rental.model.RentalDto;
import com.regalaxy.phonesin.rental.model.entity.Rental;
import com.regalaxy.phonesin.rental.model.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public boolean infoApply(RentalDetailDto rentalDetailDto, int using_date){
        Rental rental = new Rental();
        rental.setUsingDate(using_date);
        Member member = memberRepository.findById(rentalDetailDto.getMemberId()).get();
        rental.setMember(member);//사용자 정보
        rental.setApplyDate(LocalDateTime.now());//신청 날짜
        rental.setRentalStatus(1);//배송 상태 : 1 = 신청 대기
        rental.setY2K(rentalDetailDto.isY2K());
        rental.setHomecam(rentalDetailDto.isHomecam());
        rental.setClimateHumidity(rentalDetailDto.isClimateHumidity());
        rental.setCount(rentalDetailDto.getCount());//갯수
        rental.setRentalDeliveryLocation(rentalDetailDto.getRentalDeliveryLocation());//배송지
        rental.setFund(rentalDetailDto.getFund());//가격
        rental.setUsingDate(rentalDetailDto.getUsingDate());//사용 기간
        rentalRepository.save(rental);
        return true;
    }

    public boolean infoUpdated(RentalDetailDto rentalDetailDto){
        Rental rental = rentalRepository.findById(rentalDetailDto.getRentalId()).get();
        rental.setCount(rentalDetailDto.getCount());//갯수
        rental.setFund(rentalDetailDto.getFund());//가격
        rental.setHomecam(rentalDetailDto.isHomecam());
        rental.setClimateHumidity(rentalDetailDto.isClimateHumidity());
        rental.setY2K(rentalDetailDto.isY2K());
        rental.setRentalDeliveryLocation(rentalDetailDto.getRentalDeliveryLocation());//배달 주소
        rental.setUsingDate(rentalDetailDto.getUsingDate());//사용 기간
        rentalRepository.save(rental);
        return true;
    }

    public boolean infoDelete(Long rental_id){
        rentalRepository.deleteById(rental_id);
        return true;
    }

    public List<RentalDto> infoList(SearchDto searchDto){
        return rentalRepository.search(searchDto);
    }

    public RentalDetailDto info(Long return_id){
        return rentalRepository.detailInfo(return_id);
    }

    public boolean extension(Long rental_id){
        try{
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
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
    }

    public boolean apply(Long rental_id, boolean accept){
        Rental rental = rentalRepository.findById(rental_id).get();
        if(accept) {//허락
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
            rental.setRentalStatus(3);
            rentalRepository.save(rental);
        }
        return true;
    }
}
