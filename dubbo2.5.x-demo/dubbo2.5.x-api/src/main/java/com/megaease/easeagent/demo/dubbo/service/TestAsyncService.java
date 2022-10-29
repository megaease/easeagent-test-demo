package com.megaease.easeagent.demo.dubbo.service;


import com.megaease.easeagent.demo.dubbo.model.User;

public interface TestAsyncService {
	User getUser(String name) throws InterruptedException;
}
