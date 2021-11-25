/*
 * Copyright (c) 2021, MegaEase
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.megaease.easeagent.demo.rest;

import com.megaease.easeagent.demo.model.User;
import com.megaease.easeagent.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class UserRest {
    @Value("${app.name}")
    private String appName;

    @Value("${app.age}")
    private String appAge;

    @Resource
    private UserService userService;

    @RequestMapping("/user/list")
    public List<User> userList() {
        log.info("{}-{} userList invoke", this.appName, this.appAge);
        return this.userService.getUsers();
    }

    @RequestMapping("/user/add/{name}")
    public User add(@PathVariable("name") String name) {
        if (name == null || name.length() <= 0) {
            name = "easeagent-" + System.currentTimeMillis();
        }
        return this.userService.addUser(name, new Date());
    }

    @RequestMapping("/user/add")
    public User add() {
        String name = "easeagent-" + System.currentTimeMillis();
        return this.userService.addUser(name, new Date());
    }
}
