package com.megaease.easeagent.sofarpc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SofaRpcServerApplication {

	private static final Logger LOG= LoggerFactory.getLogger(SofaRpcServerApplication.class);
	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(SofaRpcServerApplication.class);
	}
}