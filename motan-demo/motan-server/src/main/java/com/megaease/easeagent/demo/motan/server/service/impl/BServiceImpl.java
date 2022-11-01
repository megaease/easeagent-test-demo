package com.megaease.easeagent.demo.motan.server.service.impl;

import com.megaease.easeagent.demo.motan.api.service.BService;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;

@MotanService(basicService = "basicServiceConfig",export = "motan:8003")
public class BServiceImpl implements BService {


	@Override
	public String hello(String name) {
		return "hello "+name;
	}
}
