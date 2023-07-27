package com.regalaxy.phonesin.back.model.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration // JpaAuditing 기능 설정
public class JpaConfig {

    // 빈으로 등록
//    @Bean
//    public AuditorAware<String> auditorAware() {
//        return new AuditorAwareImpl();
//    }
}
