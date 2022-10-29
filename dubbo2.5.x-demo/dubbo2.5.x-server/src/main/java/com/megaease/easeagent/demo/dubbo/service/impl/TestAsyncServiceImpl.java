package com.megaease.easeagent.demo.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.megaease.easeagent.demo.dubbo.model.User;
import com.megaease.easeagent.demo.dubbo.service.TestAsyncService;
import com.megaease.easeagent.demo.dubbo.service.TestService;

@Service(version = "1.0.0",
		group = "a",
		application = "${dubbo.application.id}",
		protocol = "${dubbo.protocol.id}",
		registry = "${dubbo.registry.id}",
		timeout = 3000,
		async = true
)
public class TestAsyncServiceImpl implements TestAsyncService {
	@Reference(version = "1.0.0",
			group = "b",
			application = "${dubbo.application.id}",
			timeout = 1000,
			check = false
	)
	private TestService testService;

	@Override
	public User getUser(String name) throws InterruptedException {
		return testService.getUser(name, false);
	}
}
