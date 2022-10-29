package com.magaease.easeagent.demo.dubbo.client.service.impl;

import com.magaease.easeagent.demo.dubbo.service.BService;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@DubboService(group = "dubbo-client")
public class BServiceImpl implements BService {

	private static final Logger log = LoggerFactory.getLogger(BServiceImpl.class);

	@Override
	public String test(String name) {
		log.info("server2, test called with name: {}", name);
		return name;
	}

	@Override
	public void voidTest() {

	}
}
