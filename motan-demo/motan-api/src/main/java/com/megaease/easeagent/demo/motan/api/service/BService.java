package com.megaease.easeagent.demo.motan.api.service;

import com.weibo.api.motan.transport.async.MotanAsync;

@MotanAsync
public interface BService {
    String hello(String name);
}
