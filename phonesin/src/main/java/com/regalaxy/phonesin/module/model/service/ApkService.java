package com.regalaxy.phonesin.module.model.service;

import com.regalaxy.phonesin.module.model.entity.Ytwok;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class ApkService {
    public ResponseEntity<Resource> apkDownload() throws Exception {

        String SaveFileName = "phonegojisin.apk";
        String uploadFileName = "phonegojisin.apk";

        // 한글 인코딩
        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        String absolutePath = new File("").getAbsolutePath() + "resources/" + SaveFileName;

        Resource resource = new UrlResource("file:" + absolutePath);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .header(HttpHeaders.CONTENT_TYPE, "apk")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
