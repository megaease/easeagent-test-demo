package com.magaease.easeagent.demo.dubbo.client;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = "com.magaease.easeagent.demo.dubbo.client")
@EnableDubbo(scanBasePackages = "com.magaease.easeagent.demo.dubbo.client.service")
@EnableWebMvc
public class ApacheDubboClient {


	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ApacheDubboClient.class, args);
	}

}
