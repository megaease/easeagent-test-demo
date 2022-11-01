package com.megaease.easeagent.demo.motan.api.service;

import com.megaease.easeagent.demo.motan.api.model.User;

public interface AService {
    String hello(User user,Boolean timeout);

    void testNoReturnValue();
}
