package com.megaease.easeagent.demo.motan.client.service.impl;

import com.megaease.easeagent.demo.motan.api.service.BService;
import com.megaease.easeagent.demo.motan.api.service.BServiceAsync;
import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;

@MotanService(basicService = "basicServiceConfig")
public class BServiceImpl implements BService {

    @MotanReferer(basicReferer = "basicRefererConfig",async = true)
    private BServiceAsync bServiceAsync;

    @Override
    public String hello(String name) {
        return (String) bServiceAsync.helloAsync(name).getValue();
    }
}
