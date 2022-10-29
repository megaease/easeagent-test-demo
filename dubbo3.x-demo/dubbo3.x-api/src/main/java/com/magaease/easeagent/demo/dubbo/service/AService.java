package com.magaease.easeagent.demo.dubbo.service;

import com.magaease.easeagent.demo.dubbo.model.Message;

import java.util.concurrent.CompletableFuture;

public interface AService {
	Message sayHello(String name, boolean timeout);

	default CompletableFuture<Message> sayHelloAsync(String name) {
		return CompletableFuture.completedFuture(sayHello(name, true));
	}
}
