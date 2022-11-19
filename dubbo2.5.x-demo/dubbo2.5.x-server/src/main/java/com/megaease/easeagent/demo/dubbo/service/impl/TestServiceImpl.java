package com.megaease.easeagent.demo.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.megaease.easeagent.demo.dubbo.model.User;
import com.megaease.easeagent.demo.dubbo.service.TestService;

import java.util.concurrent.TimeUnit;

@Service(version = "1.0.0",
		group = "a",
		application= "${dubbo.application.id}",
		protocol = "${dubbo.protocol.id}",
		registry = "${dubbo.registry.id}",
		retries = 0,
		timeout = 2000
)
public class TestServiceImpl implements TestService {

	@Reference(version = "1.0.0",
			group = "b",
			application = "${dubbo.application.id}",
			timeout = 1000,
			check = false
	)
	private TestService greetService;

	@Override
	public User getUser(String userName, Boolean isTimeout) throws InterruptedException {
		if(isTimeout){
			TimeUnit.SECONDS.sleep(3);
		}
		User.Address address = new User.Address();
		address.setProvinceCode(100000);
		address.setCityCode(100100);
		address.setExactAddress("test exact address");
		User user = new User();
		user.setName(userName);
		user.setAddress(address);
		return user;
	}

	@Override
	public void testNoReturnValueAndNoArgs() {

	}

}
