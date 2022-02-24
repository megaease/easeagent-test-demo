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
 *
 */
package com.megaease.easeagent.demo.employee.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/employee/async")
public class EmployeeAsyncController {
    static Logger log = LoggerFactory.getLogger(EmployeeAsyncController.class);
    static Random random = new Random();

    @GetMapping("/message")
    public WebAsyncTask<String> test(@RequestHeader MultiValueMap<String, String> headers) {
        log.debug("---------------- headers begin");
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            log.info("key: {} value: {}", entry.getKey(), entry.getValue());
        }
        log.debug("---------------- headers end");
        log.info("handler thread name: {}", Thread.currentThread().getName());

        WebAsyncTask<String> task1 = new WebAsyncTask<>(5 * 1000L, () -> {
            long delay = 200 + random.nextInt(1000);
            Thread.sleep(delay);
            log.info("task thread name: {}, delay:{}", Thread.currentThread().getName(), delay);
            return "Gateway Called in employee async Service";
        });

        return task1;
    }
}

