package com.megaease.easeagent.demo.dubbo.service;


import com.megaease.easeagent.demo.dubbo.model.User;

public interface TestService {
	User getUser(String userName, Boolean isTimeout) throws InterruptedException;

	void testNoReturnValueAndNoArgs();
}
