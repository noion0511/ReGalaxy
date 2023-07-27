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

    public boolean infoUpdated(RentalDto rentalDto){
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

    public boolean extension(int rental_id){
        return true;
    }

    public boolean apply(ApplyDto applyDto){
        return true;
    }
}
