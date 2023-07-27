package com.regalaxy.phonesin.module.model.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class ytwokService {
    public String saveProfileImage(Long uid, MultipartFile imageFile) throws Exception {
        String imagePath = null;
        String absolutePath = new File("").getAbsolutePath() + "\\";
        String path = "images/profile";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        if (!imageFile.isEmpty()) {
            String contentType = imageFile.getContentType();
            String originalFileExtension;
            if (ObjectUtils.isEmpty(contentType)) {
                throw new Exception("이미지 파일은 jpg, png 만 가능합니다.");
            } else {
                if (contentType.contains("image/jpeg")) {
                    originalFileExtension = ".jpg";
                } else if (contentType.contains("image/png")) {
                    originalFileExtension = ".png";
                } else {
                    throw new Exception("이미지 파일은 jpg, png 만 가능합니다.");
                }
            }
            imagePath = path + "/" + uid + originalFileExtension;
            file = new File(absolutePath + imagePath);
            imageFile.transferTo(file);
        }
        else {
            throw new Exception("이미지 파일이 비어있습니다.");
        }

        return imagePath;
    }
}
