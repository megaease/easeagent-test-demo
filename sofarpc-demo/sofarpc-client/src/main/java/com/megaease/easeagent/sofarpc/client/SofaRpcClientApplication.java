package com.megaease.easeagent.sofarpc.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.megaease.easeagent.sofarpc")
public class SofaRpcClientApplication {
	private static final Logger LOG = LoggerFactory.getLogger(SofaRpcClientApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SofaRpcClientApplication.class,args);
	}
}