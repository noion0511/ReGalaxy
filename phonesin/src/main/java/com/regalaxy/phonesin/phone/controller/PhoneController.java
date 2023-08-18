package com.regalaxy.phonesin.phone.controller;

import com.regalaxy.phonesin.phone.model.*;
import com.regalaxy.phonesin.phone.model.entity.Phone;
import com.regalaxy.phonesin.phone.model.service.PhoneService;
import com.regalaxy.phonesin.rental.model.RentalApplyDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/phone")
@CrossOrigin("*")
public class PhoneController {
    @Autowired
    private PhoneService phoneService;
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";


    @ApiOperation(value = "기증된 휴대폰 총 갯수")
    @GetMapping("/alldonation")//신청수정
    public ResponseEntity<?> allDonation(){
        int cnt = phoneService.allDonationPhone();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("donation phone cnt", cnt);
        resultMap.put("message", SUCCESS);
        resultMap.put("status", 200);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
}
