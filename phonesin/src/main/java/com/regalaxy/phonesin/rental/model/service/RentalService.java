package com.regalaxy.phonesin.rental.model.service;

import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.member.model.entity.Member;
import com.regalaxy.phonesin.member.model.repository.MemberRepository;
import com.regalaxy.phonesin.phone.model.repository.PhoneRepository;
import com.regalaxy.phonesin.rental.model.ApplyDto;
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
    private RentalRepository rentalrepository;
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private MemberRepository memberRepository;

    public boolean infoApply(RentalDetailDto rentalDetailDto){
        Rental rental = new Rental();
        rental.setApply_date(LocalDateTime.now());
        rental.setCount(rentalDetailDto.getCount());
        rental.setFund(rentalDetailDto.getFund());
        rental.setHomecam(rentalDetailDto.isHomecam());
        rental.setClimateHumidity(rentalDetailDto.isClimateHumidity());
        rental.setY2K(rentalDetailDto.isY2K());
        rental.setRental_deliverylocation(rentalDetailDto.getRental_deliverylocation());
        Member member = memberRepository.findById(rentalDetailDto.getMember_id()).get();
        rental.setMember(member);
        rentalrepository.save(rental);
        return true;
    }

    public boolean infoUpdated(RentalDetailDto rentalDetailDto){
        Rental rental = new Rental();
        rental.setRental_id(rentalDetailDto.getRental_id());
        rental.setApply_date(rentalrepository.findById(rentalDetailDto.getRental_id()).get().getApply_date());
        rental.setCount(rentalDetailDto.getCount());
        rental.setFund(rentalDetailDto.getFund());
        rental.setHomecam(rentalDetailDto.isHomecam());
        rental.setClimateHumidity(rentalDetailDto.isClimateHumidity());
        rental.setY2K(rentalDetailDto.isY2K());
        rental.setRental_deliverylocation(rentalDetailDto.getRental_deliverylocation());
        Member member = memberRepository.findById(rentalDetailDto.getMember_id()).get();
        rental.setMember(member);
        rentalrepository.save(rental);
        return true;
    }

    public boolean infoDelete(int rental_id){
        return true;
    }

    public List<RentalDto> infoList(SearchDto searchDto){
        return rentalrepository.search(searchDto);
    }

    public RentalDetailDto info(int return_id){
        return rentalrepository.detailInfo(return_id);
    }

    public boolean extension(Long rental_id){
        try{
            Rental rental = rentalrepository.findById(rental_id).get();
            if(rental.isExtension()){//연장한 적 있다면
                return false;
            }else{
                rental.extension();//isExtension을 true로 변경
                int month = rental.getRental_end().getDayOfMonth() + 6;//6개월 연장
                int year = rental.getRental_end().getYear();
                if(month>12){
                    rental.setRental_end(rental.getRental_end().withMonth(month-12).withYear(year+1));
                }else{
                    rental.setRental_end(rental.getRental_end().withMonth(month));
                }
//                rentalrepository.save(rental);
                return true;
            }
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
    }

    public boolean apply(ApplyDto applyDto){
        return true;
    }
}
