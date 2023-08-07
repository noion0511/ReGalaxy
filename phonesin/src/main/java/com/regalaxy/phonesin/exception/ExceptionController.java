package com.regalaxy.phonesin.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestController
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> Exception(Exception e){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("error", e.getMessage());
        map.put("error2", e.toString());
//        map.put("status", 500);
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}
