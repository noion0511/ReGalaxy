package com.regalaxy.phonesin.module.model.entity;
import com.regalaxy.phonesin.module.model.repository.YtwokRepository;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class MyQuartzJob implements Job {
    private static final SimpleDateFormat TIMESTAMP_FMT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSSS");

    @Autowired
    private YtwokRepository ytwokRepository; // 예시로 MyService를 주입받음

//    public MyQuartzJob(YtwokRepository ytwokRepository) {
//        this.ytwokRepository = ytwokRepository;
//    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Job Executed [" + new Date(System.currentTimeMillis()) + "]");

        /**
         * JobData에 접근
         */
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        String currentDate = TIMESTAMP_FMT.format(new Date());
        String triggerKey = context.getTrigger().getKey().toString(); // group1.trggerName

        String jobSays = dataMap.getString("jobSays"); // Hello World!
        float myFloatValue = dataMap.getFloat("myFloatValue"); // 3.141
        String fileName = dataMap.getString("fileName");

        System.out.println(String.format("[%s][%s] %s %s %s", currentDate, triggerKey, jobSays, myFloatValue, fileName));

//        System.out.println("id : " + ytwokRepository.findBySaveFile(fileName).getYtwokId());

    }
}
