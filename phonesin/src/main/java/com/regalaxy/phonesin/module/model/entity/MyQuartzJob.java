//package com.regalaxy.phonesin.module.model.entity;
//import com.regalaxy.phonesin.module.model.entity.Ytwok;
//import com.regalaxy.phonesin.module.model.repository.YtwokRepository;
//import com.regalaxy.phonesin.module.model.service.YtwokService;
//import lombok.RequiredArgsConstructor;
//import org.quartz.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.util.UriUtils;
//
//import javax.transaction.Transactional;
//import java.io.File;
//import java.nio.charset.StandardCharsets;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@Component
//@PersistJobDataAfterExecution
//@DisallowConcurrentExecution
//@RequiredArgsConstructor
//@Transactional
//public class MyQuartzJob implements Job {
//    private static final SimpleDateFormat TIMESTAMP_FMT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSSS");
//    @Override
//    public void execute(JobExecutionContext context) throws JobExecutionException {
//        System.out.println("Job Executed [" + new Date(System.currentTimeMillis()) + "]");
//
//        /**
//         * JobData에 접근
//         */
//        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
//
//        String currentDate = TIMESTAMP_FMT.format(new Date());
//        String triggerKey = context.getTrigger().getKey().toString(); // group1.trggerName
//
//        String jobSays = dataMap.getString("jobSays"); // Hello World!
//        float myFloatValue = dataMap.getFloat("myFloatValue"); // 3.141
//        String saveFileName = dataMap.getString("saveFileName");
//        String uploadFileName = dataMap.getString("uploadFileName");
//
//        System.out.println(String.format("[%s][%s] %s %s %s", currentDate, triggerKey, jobSays, myFloatValue, saveFileName));
//
////        System.out.println("id : " + ytwokRepository.findBySaveFile(fileName).getYtwokId());
////        ytwokService.deleteImage(fileName);
//
//        // 한글 인코딩
//        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
//        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
//
//        String absolutePath = new File("").getAbsolutePath() + "resources/images/y2k/" + saveFileName;
//        File file = new File(absolutePath);
//        file.delete();
//    }
//}
