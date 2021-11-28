package com.megaease.easeagent.damo.eurekawebclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EurekaWebclientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaWebclientApplication.class, args);
    }

}
