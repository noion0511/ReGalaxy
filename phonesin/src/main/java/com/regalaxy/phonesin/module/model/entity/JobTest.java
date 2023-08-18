//package com.regalaxy.phonesin.module.model.entity;
//
//import com.regalaxy.phonesin.module.model.repository.YtwokRepository;
//import org.quartz.JobDataMap;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.springframework.scheduling.quartz.QuartzJobBean;
//import org.springframework.stereotype.Component;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@Component
//public class JobTest extends QuartzJobBean {
//    private static final SimpleDateFormat TIMESTAMP_FMT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSSS");
//
////    private final YtwokRepository ytwokRepository;
////
////    public JobTest(YtwokRepository ytwokRepository) {
////        this.ytwokRepository = ytwokRepository;
////    }
//
//
//    @Override
//    protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
//        System.out.println("Job Executed [" + new Date(System.currentTimeMillis()) + "]");
//
//        /**
//         * JobData에 접근
//         */
//        JobDataMap dataMap = ctx.getJobDetail().getJobDataMap();
//
//        String currentDate = TIMESTAMP_FMT.format(new Date());
//        String triggerKey = ctx.getTrigger().getKey().toString(); // group1.trggerName
//
//        String jobSays = dataMap.getString("jobSays"); // Hello World!
//        float myFloatValue = dataMap.getFloat("myFloatValue"); // 3.141
//        String fileName = dataMap.getString("fileName");
//
//        System.out.println(String.format("[%s][%s] %s %s %s", currentDate, triggerKey, jobSays, myFloatValue, fileName));
//
////        ytwokRepository.findAll();
//    }
//}