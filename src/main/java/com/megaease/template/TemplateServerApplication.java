package com.megaease.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableCaching
@EnableScheduling
@EnableAsync
@EnableAspectJAutoProxy
public class TemplateServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TemplateServerApplication.class, args);
    }
}
