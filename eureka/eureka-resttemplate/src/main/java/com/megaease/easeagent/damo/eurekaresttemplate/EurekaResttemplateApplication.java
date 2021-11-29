package com.megaease.easeagent.damo.eurekaresttemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EurekaResttemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaResttemplateApplication.class, args);
    }

}
