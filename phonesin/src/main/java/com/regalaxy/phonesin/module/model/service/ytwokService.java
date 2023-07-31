package com.regalaxy.phonesin.module.model.service;

import com.regalaxy.phonesin.module.model.entity.Ytwok;
import com.regalaxy.phonesin.module.model.repository.YtwokRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class YtwokService {

    private final YtwokRepository ytwokRepository;


    public void saveImage(List<MultipartFile> files) throws Exception {
        String absolutePath = new File("").getAbsolutePath() + "\\";
        System.out.println(absolutePath);

        String uploadPath = absolutePath + "images/y2k";

        if (!files.isEmpty()) {
            String today = new SimpleDateFormat("yyMMdd").format(new Date());
            String saveFolder = uploadPath + File.separator + today;
//            logger.debug("저장 폴더 : {}", saveFolder);
            File folder = new File(saveFolder);
            if (!folder.exists())
                folder.mkdirs();
            List<Ytwok> fileInfos = new ArrayList<Ytwok>();
            for (MultipartFile mfile : files) {
                String originalFileName = mfile.getOriginalFilename();
                String saveFileName = originalFileName;
                if (!originalFileName.isEmpty()) {
                    saveFileName = UUID.randomUUID().toString()
                            + originalFileName.substring(originalFileName.lastIndexOf('.'));
//                    logger.debug("원본 파일 이름 : {}, 실제 저장 파일 이름 : {}", mfile.getOriginalFilename(), saveFileName);
                    mfile.transferTo(new File(folder, saveFileName));
                }
                Ytwok ytwok = Ytwok.builder()
                        .SaveFolder(today)
                        .OriginalFile(originalFileName)
                        .SaveFile(saveFileName)
                        .build();
                fileInfos.add(ytwok);
                ytwokRepository.save(ytwok);
            }
        }

    }

}
