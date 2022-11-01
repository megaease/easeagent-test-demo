package com.megaease.easeagent.demo.motan.server.service.impl;

import com.megaease.easeagent.demo.motan.api.model.User;
import com.megaease.easeagent.demo.motan.api.service.AService;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;

import java.util.concurrent.TimeUnit;

@MotanService(basicService = "basicServiceConfig")
public class AServiceImpl implements AService {


    @Override
    public String hello(User user,Boolean timeout) {
        if (timeout) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return user.getName();
    }

    @Override
    public void testNoReturnValue() {

    }
}
