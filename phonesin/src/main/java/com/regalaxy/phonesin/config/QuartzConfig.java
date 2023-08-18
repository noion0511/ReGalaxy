//package com.regalaxy.phonesin.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.config.PropertiesFactoryBean;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.scheduling.quartz.SchedulerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.io.IOException;
//import java.util.Properties;
//
//@Configuration
//@RequiredArgsConstructor
//public class QuartzConfig {
//    private final DataSource dataSource;
//    private final ApplicationContext applicationContext;
//    private final PlatformTransactionManager platformTransactionManager;
//
//
//    public SchedulerFactoryBean schedulerFactoryBean(){
//        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
//        AutoWiringSpringBeanJobFactory autoWiringSpringBeanJobFactory = new AutoWiringSpringBeanJobFactory();
//        autoWiringSpringBeanJobFactory.setApplicationContext(applicationContext);
//        schedulerFactoryBean.setJobFactory(autoWiringSpringBeanJobFactory);
//        schedulerFactoryBean.setDataSource(dataSource);
//        schedulerFactoryBean.setOverwriteExistingJobs(true);
//        schedulerFactoryBean.setAutoStartup(true);
//        schedulerFactoryBean.setTransactionManager(platformTransactionManager);
//        schedulerFactoryBean.setQuartzProperties(quartzProperties());
//        return schedulerFactoryBean;
//    }
//
//    private Properties quartzProperties(){
//        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
//        propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));
//        Properties properties = null;
//        try {
//            propertiesFactoryBean.afterPropertiesSet();
//            properties = propertiesFactoryBean.getObject();
//        } catch (IOException e) {
//            System.out.println("quartzProperties parse error : " + e.toString());
//        }
//        return properties;
//    }
//}
