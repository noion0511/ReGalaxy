package com.regalaxy.phonesin.module.controller;

import com.regalaxy.phonesin.module.model.service.YtwokService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/ytwok")
@Api(value = "y2k API", description = "y2k Controller")
public class YtwokController {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    private final YtwokService ytwokService;

    @ApiOperation(value = "이미지업로드")
    @PostMapping("/apply")
    public ResponseEntity<Map<String, Object>> ytwokapply(
            @RequestParam("file") MultipartFile file
            ) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap.put("photos", ytwokService.saveImage(file));
            resultMap.put("message", SUCCESS);
            resultMap.put("status", 201);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            resultMap.put("error", e.toString());
            resultMap.put("message", FAIL);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "이미지다운로드")
    @GetMapping(value = "images/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable("fileName") String fileName) {
        try {
            ResponseEntity<Resource> result = ytwokService.loadImage(fileName);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @ApiOperation(value = "QR 이미지 링크")
    @GetMapping(value = "qr/{fileId}")
    public ResponseEntity<Map<String, Object>> QRUrl(@PathVariable("fileId") long fileId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap.put("QR url", "https://chart.apis.google.com/chart?cht=qr&chs=300x300&chl=http://i9d102.p.ssafy.io:8080/ytwok/images/" + fileId);
            resultMap.put("message", SUCCESS);
            resultMap.put("status", 200);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            resultMap.put("error", e.toString());
            resultMap.put("message", FAIL);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "y2k이미지 삭제")
    @DeleteMapping(value = "images/{fileName}")
    public ResponseEntity<Map<String, Object>> deleteY2k(@PathVariable("fileName") String fileName) {
        Map<String, Object> resultMap = new HashMap<String, Object>();



        resultMap.put("message", SUCCESS);
        resultMap.put("status", 200);
        return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
    }
}
