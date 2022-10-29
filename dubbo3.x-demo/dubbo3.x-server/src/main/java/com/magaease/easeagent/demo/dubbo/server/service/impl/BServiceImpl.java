package com.magaease.easeagent.demo.dubbo.server.service.impl;

import com.magaease.easeagent.demo.dubbo.model.Message;
import com.magaease.easeagent.demo.dubbo.service.AService;
import com.magaease.easeagent.demo.dubbo.service.BService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@DubboService(group = "dubbo-server")
public class BServiceImpl implements BService {

	private static final Logger log = LoggerFactory.getLogger(BServiceImpl.class);

	@DubboReference(group = "dubbo-client", timeout = 3000, retries = 0)
	private BService bService;

	@DubboReference(group = "dubbo-client", timeout = 3000, retries = 0)
	private AService aService;

	@Override
	public String test(String name) {
		Message result = aService.sayHello(name, false);
		return result.getData();
	}

	@Override
	public void voidTest() {
		bService.voidTest();
	}
}
