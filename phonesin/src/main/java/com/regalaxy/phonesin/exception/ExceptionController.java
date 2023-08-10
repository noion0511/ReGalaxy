package com.regalaxy.phonesin.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Map<String, Object>> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("message", "필요한 헤더가 누락되었습니다: " + ex.getHeaderName());
        resultMap.put("status", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(resultMap, HttpStatus.BAD_REQUEST);
    }
}
