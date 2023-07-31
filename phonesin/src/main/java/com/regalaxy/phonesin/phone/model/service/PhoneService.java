package com.regalaxy.phonesin.phone.model.service;

import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.phone.model.PhoneDto;
import com.regalaxy.phonesin.phone.model.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneService {
    @Autowired
    private PhoneRepository phoneRepository;

    public List<PhoneDto> list(SearchDto searchDto){
        phoneRepository.search(searchDto);
        return null;
    }
}
