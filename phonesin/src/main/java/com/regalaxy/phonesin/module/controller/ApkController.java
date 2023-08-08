package com.regalaxy.phonesin.module.controller;


import com.regalaxy.phonesin.module.model.service.ApkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@Controller
@RequiredArgsConstructor
@RequestMapping("/download")
@Api(value = "apk 배포용", description = "apk 배포 controller")
public class ApkController {

    private final ApkService apkService;

    @ApiOperation(value = "이미지다운로드")
    @GetMapping(value = "/phonegojisin")
    public ResponseEntity<Resource> getApk() {
        try {
            ResponseEntity<Resource> result = apkService.apkDownload();
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
