package com.regalaxy.phonesin.module.model.service;

import com.regalaxy.phonesin.module.model.YtwokDto;
import com.regalaxy.phonesin.module.model.entity.Ytwok;
import com.regalaxy.phonesin.module.model.repository.YtwokRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
public class YtwokService {

    private final YtwokRepository ytwokRepository;

    public YtwokDto saveImage(MultipartFile file) throws Exception {
        String contentType = file.getContentType();
        String fileType;
        //파일의 Content Type 이 있을 경우 Content Type 기준으로 확장자 확인
        if (StringUtils.hasText(contentType)) {
            if (contentType.equals("image/jpg")) {
                fileType = "jpg";
            } else if (contentType.equals("image/png")) {
                fileType = "png";
            } else if (contentType.equals("image/jpeg")) {
                fileType = "jpeg";
            }else if (contentType.equals("image/*")) {
                fileType = "*";
            }
            else{
                // contentType 존재하지 않는 경우 처리
                throw new Exception("사진 파일이 아닙니다.");
            }
            String uploadPath = new File("").getAbsolutePath() + "/images/y2k";
            Ytwok resultEntity = null;
            // 파일이 비었는지 검증
            if (!file.isEmpty()) {

                String saveFolder = uploadPath + File.separator;

                File folder = new File(saveFolder);

                // 폴더가 존재하는지 검증
                if (!folder.exists())
                    folder.mkdirs();

                String originalFileName = file.getOriginalFilename();
                String saveFileName = originalFileName;

                // file name 중복경우 예외처리
                if (!originalFileName.isEmpty()) {
                    saveFileName = UUID.randomUUID().toString()
                            + originalFileName.substring(originalFileName.lastIndexOf('.'));
                    file.transferTo(new File(folder, saveFileName));
                }

                // Entity build
                Ytwok ytwok = Ytwok.builder()
                        .originalFile(originalFileName)
                        .saveFile(saveFileName)
                        .contentType(fileType)
                        .build();

                resultEntity = ytwokRepository.saveAndFlush(ytwok);

            }
            return YtwokDto.builder()
                    .ytwokId(resultEntity.getYtwokId())
                    .saveFile(resultEntity.getSaveFile())
                    .originalFile(resultEntity.getOriginalFile())
                    .contentType("image/" + resultEntity.getContentType())
                    .build();
        }
        else{
            // contentType 존재하지 않는 경우 처리
            throw new Exception("유효한 확장자를 가진 파일이(가) 아닙니다.");
        }
    }

    public ResponseEntity<Resource> loadImage(String fileName) throws Exception {
        Ytwok file = ytwokRepository.findBySaveFile(fileName);

        // file not found
        if (file == null) return null;

        String SaveFileName = file.getSaveFile();
        String uploadFileName = file.getOriginalFile();

        // 한글 인코딩
        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        String absolutePath = new File("").getAbsolutePath() + "/images/y2k/" + SaveFileName;
        System.out.println("filePath : " + absolutePath);
        Resource resource = new UrlResource("file:" + absolutePath);
        System.out.println("file Lodding Complite");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .header(HttpHeaders.CONTENT_TYPE, file.getContentType())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
    public void deleteImage(String fileName){

    }
}
