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
            @RequestParam("files") List<MultipartFile> files
            ) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap.put("photos", ytwokService.saveImage(files));
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.toString());
            resultMap.put("message", FAIL);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }
    }

    @ApiOperation(value = "이미지다운로드")
    @GetMapping(value = "images/{fileId}")
    public ResponseEntity<UrlResource> getImage(@PathVariable("fileId") long fileId) {
        try {
            return ytwokService.loadImage(fileId);
        } catch (Exception e) {
            return null;
        }
    }

}
