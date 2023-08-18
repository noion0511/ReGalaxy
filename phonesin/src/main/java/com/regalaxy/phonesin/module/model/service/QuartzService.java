//package com.regalaxy.phonesin.module.model.service;
//
//import com.regalaxy.phonesin.module.model.entity.MyQuartzJob;
//import lombok.RequiredArgsConstructor;
//import org.quartz.*;
//import org.quartz.impl.StdSchedulerFactory;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.time.format.TextStyle;
//import java.util.Date;
//import java.util.Locale;
//import java.util.Map;
//
//import static org.quartz.JobBuilder.newJob;
//
//@RequiredArgsConstructor
//@Service
//public class QuartzService {
//    private final Scheduler scheduler;
//
//    public void start(String saveFileName, String uploadFileName) throws SchedulerException, InterruptedException {
//
//        // Scheduler 사용을 위한 인스턴스화
//        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
//
//        // JOB Data 객체
//        JobDataMap jobDataMap = new JobDataMap();
//        jobDataMap.put("jobSays", "Say Hello World!");
//        jobDataMap.put("myFloatValue", 3.1415f);
//        jobDataMap.put("saveFileName", saveFileName);
//        jobDataMap.put("uploadFileName", uploadFileName);
//
////        JobDetail jobDetail = JobBuilder.newJob(JobTest.class)
////                .withIdentity("myJob", "group1")
////                .setJobData(jobDataMap)
////                .build();
//
//        JobDetail jobDetail = JobBuilder.newJob(MyQuartzJob.class)
//                .withIdentity(saveFileName, "group1")
//                .setJobData(jobDataMap)
//                .build();
//
//        LocalDateTime later = LocalDateTime.now().plusDays(90);
//        Date date = java.sql.Timestamp.valueOf(later);
//        // SimpleTrigger
//        @SuppressWarnings("deprecation")
//        SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger()
//                .withIdentity(saveFileName, "simple_trigger_group")
//                .startAt(date)
//                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//                        .withRepeatCount(0))
//                .forJob(jobDetail)
//                .build();
//
//        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
//        scheduler.scheduleJob(jobDetail, simpleTrigger);
//        scheduler.start();
//        System.out.println("scheduler start");
//    }
//
//    public void addSimpleJob(Class job, String name, String desc, Map params, Integer seconds) throws SchedulerException {
//        JobDetail jobDetail = buildJobDetail(job, name, desc, params);
//
//        if (scheduler.checkExists(jobDetail.getKey())) {
//            scheduler.deleteJob(jobDetail.getKey());
//        }
//
//        scheduler.scheduleJob(
//                jobDetail,
//                buildSimpleJobTrigger(seconds)
//        );
//    }
//
//    public void addCronJob(Class job, String name, String desc, Map params, String expression) throws SchedulerException {
//        JobDetail jobDetail = buildJobDetail(job, name, desc, params);
//
//        if (scheduler.checkExists(jobDetail.getKey())) {
//            scheduler.deleteJob(jobDetail.getKey());
//        }
//
//        scheduler.scheduleJob(
//                jobDetail,
//                buildCronJobTrigger(expression)
//        );
//    }
//
//    private JobDetail buildJobDetail(Class job, String name, String desc, Map params) {
//        JobDataMap jobDataMap = new JobDataMap();
//        if(params != null) jobDataMap.putAll(params);
//        return newJob(job)
//                .withIdentity(name)
//                .withDescription(desc)
//                .usingJobData(jobDataMap)
//                .build();
//    }
//
//    /**
//     * Cron Job Trigger
//     */
//    // *  *   *   *   *   *     *
//    // 초  분  시   일   월  요일  년도(생략가능)
//    private Trigger buildCronJobTrigger(String scheduleExp) {
//        return TriggerBuilder.newTrigger()
//                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp))
//                .build();
//    }
//
//    /**
//     * Simple Job Trigger
//     */
//    private Trigger buildSimpleJobTrigger(Integer seconds) {
//        return TriggerBuilder.newTrigger()
//                .withSchedule(SimpleScheduleBuilder
//                        .simpleSchedule()
//                        .repeatForever()
//                        .withIntervalInSeconds(seconds))
//                .build();
//    }
//
//    public static String buildCronExpression(LocalDateTime time) {
//        LocalDateTime fireTime = time.plusSeconds(10);
//        // 0 0 0 15 FEB ? 2021
//        return String.format("%d %d %d %d %s ? %d", fireTime.getSecond(), fireTime.getMinute(), fireTime.getHour(), fireTime.getDayOfMonth(), fireTime.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toUpperCase(), fireTime.getYear());
//    }
//}