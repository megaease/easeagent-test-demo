package com.magaease.easeagent.demo.dubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo(scanBasePackages = "com.magaease.easeagent.demo.dubbo.server.service")
public class ApacheDubboServer {

	public static void main(String[] args) {
		SpringApplication.run(ApacheDubboServer.class, args);
		System.out.println("doubbo server started");
	}

}
