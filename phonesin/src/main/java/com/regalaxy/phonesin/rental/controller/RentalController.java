package com.regalaxy.phonesin.rental.controller;

import com.regalaxy.phonesin.member.model.SearchDto;
import com.regalaxy.phonesin.rental.model.RentalDto;
import com.regalaxy.phonesin.rental.model.service.RentalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rental")
@CrossOrigin("*")
public class RentalController {
    private RentalService rentalService;
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @PostMapping("/info/apply")
    public ResponseEntity<?> infoApply(RentalDto rentalDto){
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @PutMapping("/info/update")
    public ResponseEntity<?> infoUpdate(RentalDto rentalDto){
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @DeleteMapping("/info/delete")
    public ResponseEntity<?> infoDelete(int rental_id){
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/info/list")
    public ResponseEntity<?> infoList(SearchDto searchDto){
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @GetMapping("/info/info")
    public ResponseEntity<?> info(int rental_id){
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }

    @PutMapping("/info/extension")
    public ResponseEntity<?> extension(int rental_id){
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }
}
