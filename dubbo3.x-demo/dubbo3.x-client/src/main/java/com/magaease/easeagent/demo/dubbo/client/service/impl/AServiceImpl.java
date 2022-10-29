package com.magaease.easeagent.demo.dubbo.client.service.impl;

import com.magaease.easeagent.demo.dubbo.model.Message;
import com.magaease.easeagent.demo.dubbo.service.AService;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DubboService(group = "dubbo-client")
public class AServiceImpl implements AService {

	private static Logger log = LoggerFactory.getLogger(AServiceImpl.class);

	@Override
	public Message sayHello(String name, boolean timeout) {
		Message message = new Message();
		message.setData("hello-"+name);
		return message;
	}
}
