package com.regalaxy.phonesin.module.controller;

import com.regalaxy.phonesin.module.model.entity.Ytwok;
import com.regalaxy.phonesin.module.model.repository.YtwokRepository;
import com.regalaxy.phonesin.module.model.service.YtwokService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/ytwok")
@Api(value = "y2k API", description = "y2k Controller")
public class YtwokController {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    private final YtwokService ytwokService;
    private final YtwokRepository ytwokRepository;

    @ApiOperation(value = "이미지업로드")
    @PostMapping("/apply")
    public ResponseEntity<Map<String, Object>> ytowkapply(
            @RequestParam("files") List<MultipartFile> files
            ) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            ytwokService.saveImage(files);
            resultMap.put("message", SUCCESS);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.toString());
            resultMap.put("message", FAIL);
            return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
        }
    }


    @GetMapping(value = "images/{fileOriginName}")
    public ResponseEntity<UrlResource> getItemImageByName(@PathVariable("fileOriginName") long fileName) throws IOException {
        try {
            Optional<Ytwok> findFile = ytwokRepository.findById(fileName);
            Ytwok file = findFile.orElse(null);
            if (file == null) return null;
            String SaveFileName = file.getSaveFile();
            String uploadFileName = file.getOriginalFile();

            String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
            String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";


            String absolutePath = new File("").getAbsolutePath() + "\\";
            String uploadPath = absolutePath + "images/y2k/230731/" + SaveFileName;


            UrlResource resource = new UrlResource("file:" + uploadPath);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(resource);
        } catch (Exception e) {
            return null;
        }
    }
}
