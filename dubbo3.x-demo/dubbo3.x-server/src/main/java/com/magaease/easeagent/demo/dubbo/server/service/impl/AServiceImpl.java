package com.magaease.easeagent.demo.dubbo.server.service.impl;

import com.magaease.easeagent.demo.dubbo.model.Message;
import com.magaease.easeagent.demo.dubbo.service.AService;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

@DubboService(group = "dubbo-server", timeout = 2300)
public class AServiceImpl implements AService {

    private static Logger log = LoggerFactory.getLogger(AServiceImpl.class);

    @Override
    public Message sayHello(String name, boolean timeout) {
        if (timeout) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Message message = new Message();
        message.setData("Hi! " + name);
        return message;
    }
}
