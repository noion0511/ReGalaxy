package com.regalaxy.phonesin.phone.model.service;

import com.regalaxy.phonesin.donation.model.repository.DonationRepository;
import com.regalaxy.phonesin.phone.model.ModelDto;
import com.regalaxy.phonesin.phone.model.PhoneApplyDto;
import com.regalaxy.phonesin.phone.model.PhoneDto;
import com.regalaxy.phonesin.phone.model.PhoneSearchDto;
import com.regalaxy.phonesin.phone.model.entity.Model;
import com.regalaxy.phonesin.phone.model.entity.Phone;
import com.regalaxy.phonesin.phone.model.repository.ModelRepository;
import com.regalaxy.phonesin.phone.model.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhoneService {
    @Autowired
    private PhoneRepository phoneRepository;
    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private ModelRepository modelRepository;

    public List<PhoneDto> list(PhoneSearchDto phoneSearchDto){
        return phoneRepository.search(phoneSearchDto);
    }

    public void apply(PhoneApplyDto phoneApplyDto){
        Phone phone = new Phone();
        phone.setDonation(donationRepository.getReferenceById(phoneApplyDto.getDonationId()));
        phone.setY2K(phoneApplyDto.isY2K());
        phone.setHomecam(phoneApplyDto.isHomecam());
        phone.setClimateHumidity(phoneApplyDto.isClimateHumidity());
        phone.setSerialNumber(phoneApplyDto.getSerialNumber());
        phoneRepository.save(phone);
    }

    public void update(PhoneApplyDto phoneApplyDto){
        Phone phone = phoneRepository.findById(phoneApplyDto.getPhoneId()).get();
        if(phoneApplyDto.getDonationId()!=0) {
            phone.setDonation(donationRepository.getReferenceById(phoneApplyDto.getDonationId()));
        }
        Model model = modelRepository.findById(phoneApplyDto.getModelId()).get();
        phone.setModel(model);
        phone.setY2K(phoneApplyDto.isY2K());
        phone.setHomecam(phoneApplyDto.isHomecam());
        phone.setClimateHumidity(phoneApplyDto.isClimateHumidity());
        phone.setSerialNumber(phoneApplyDto.getSerialNumber());
        phone.setPhoneId(phoneApplyDto.getPhoneId());
        phoneRepository.save(phone);
    }

    public void delete(Long phone_id){
        phoneRepository.deleteById(phone_id);
    }

    public List<ModelDto> modelList(){
        List<Model> model = modelRepository.findAll();
        List<ModelDto> list = new ArrayList<>();
        for(Model m : model){
            ModelDto modelDto = new ModelDto();
            modelDto.setNickname(m.getNickname());
            modelDto.setModelName(m.getModelName());
            modelDto.setModelId(m.getModelId());
            list.add(modelDto);
        }
        return list;
    }

    public int allDonationPhone(){
        return donationRepository.countByDonationStatusEquals(3);
    }
}
