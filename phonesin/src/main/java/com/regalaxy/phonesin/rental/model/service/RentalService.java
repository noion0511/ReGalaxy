package com.regalaxy.phonesin.rental.model.service;

import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.phone.model.repository.PhoneRepository;
import com.regalaxy.phonesin.rental.model.ApplyDto;
import com.regalaxy.phonesin.rental.model.RentalDetailDto;
import com.regalaxy.phonesin.rental.model.RentalDto;
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

    public boolean infoApply(RentalDto rentalDto){ return true; }

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
