package com.regalaxy.phonesin.module.model.service;

import com.regalaxy.phonesin.module.model.YtwokDto;
import com.regalaxy.phonesin.module.model.entity.Ytwok;
import com.regalaxy.phonesin.module.model.repository.YtwokRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class YtwokService {

    private final YtwokRepository ytwokRepository;

    public List<YtwokDto> saveImage(List<MultipartFile> files) throws Exception {
        String uploadPath = new File("").getAbsolutePath() + "\\" + "images/y2k";
        List<YtwokDto> result = new ArrayList<>();

        // 파일이 비었는지 검증
        if (!files.isEmpty()) {

            String saveFolder = uploadPath + File.separator;

            File folder = new File(saveFolder);

            // 폴더가 존재하는지 검증
            if (!folder.exists())
                folder.mkdirs();

            // 파일당 반복
            for (MultipartFile mfile : files) {
                String originalFileName = mfile.getOriginalFilename();
                String saveFileName = originalFileName;

                // file name 중복경우 예외처리
                if (!originalFileName.isEmpty()) {
                    saveFileName = UUID.randomUUID().toString()
                            + originalFileName.substring(originalFileName.lastIndexOf('.'));
                    mfile.transferTo(new File(folder, saveFileName));
                }

                // Entity build
                Ytwok ytwok = Ytwok.builder()
                        .originalFile(originalFileName)
                        .saveFile(saveFileName)
                        .build();

                Ytwok resultEntity = ytwokRepository.saveAndFlush(ytwok);

                // 결과에 저장
                result.add(YtwokDto.builder()
                        .ytwokId(resultEntity.getYtwokId())
                        .saveFile(resultEntity.getSaveFile())
                        .originalFile(resultEntity.getOriginalFile())
                        .build()
                );
            }
        }
        return result;
    }

    public ResponseEntity<UrlResource> loadImage(long fileId) throws Exception {
        Ytwok file = ytwokRepository.findById(fileId).orElse(null);

        // file not found
        if (file == null) return null;

        String SaveFileName = file.getSaveFile();
        String uploadFileName = file.getOriginalFile();

        // 한글 인코딩
        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        String absolutePath = new File("").getAbsolutePath() + "\\" + "images/y2k/" + SaveFileName;

        UrlResource resource = new UrlResource("file:" + absolutePath);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

}
