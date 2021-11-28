package com.megaease.easeagent.damo.eurekaregisters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EurekaRegistersApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaRegistersApplication.class, args);
    }

}
