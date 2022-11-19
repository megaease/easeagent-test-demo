package com.megaease.easeagent.demo.dubbo.servicex.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.megaease.easeagent.demo.dubbo.model.User;
import com.megaease.easeagent.demo.dubbo.service.TestService;

@Service(version = "1.0.0",
		group = "b",
		application = "${dubbo.application.id}",
		protocol = "${dubbo.protocol.id}",
		registry = "${dubbo.registry.id}",
		timeout = 3000
)
public class TestServiceImpl implements TestService {

	@Override
	public User getUser(String userName, Boolean isTimeout) {
		return new User();
	}

	@Override
	public void testNoReturnValueAndNoArgs() {

	}

}
