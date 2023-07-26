package com.regalaxy.phonesin.rental.model.service;

import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.rental.model.ApplyDto;
import com.regalaxy.phonesin.rental.model.RentalDetailDto;
import com.regalaxy.phonesin.rental.model.RentalDto;

import java.util.ArrayList;

public class RentalService {

    public boolean infoApply(RentalDto rentalDto){
        return true;
    }

    public boolean infoUpdated(RentalDto rentalDto){
        return true;
    }

    public boolean infoDelete(int rental_id){
        return true;
    }

    public ArrayList<RentalDetailDto> infoList(SearchDto searchDto){
        return new ArrayList<>();
    }

    public RentalDetailDto info(int return_id){
        return new RentalDetailDto();
    }

    public boolean extension(int rental_id){
        return true;
    }

    public boolean apply(ApplyDto applyDto){
        return true;
    }
}
