/*
 * Copyright (c) 2017, MegaEase
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

package com.megaease.easeagent.damo.eurekaregisters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingController.class.getName());
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    ServerConfig serverConfig;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name, @RequestHeader Map<String, String> headers) {
        LOGGER.info("---------------------  headers begin  -----------------------");
        headers.forEach((key, value) -> {
            LOGGER.info(String.format("Header '%s' = %s", key, value));
        });
        LOGGER.info("---------------------  headers end  -----------------------\n\n");
        LOGGER.info("name: {}", name);
        return String.format("Id<%s> Port<%s>, Hello %s!", counter.incrementAndGet(), serverConfig.getPort(), name);
    }

    @GetMapping("/greeting_async")
    public Callable<String> greetingAsync(@RequestParam(value = "name", defaultValue = "World") String name, @RequestHeader Map<String, String> headers) {
        LOGGER.info("---------------------  headers begin  -----------------------");
        headers.forEach((key, value) -> {
            LOGGER.info(String.format("Header '%s' = %s", key, value));
        });
        LOGGER.info("---------------------  headers end  -----------------------\n\n");
        return () -> {
            System.out.println("---------------");
            return String.format("Id<%s> Port<%s>, Hello %s!", counter.incrementAndGet(), serverConfig.getPort(), name);
        };
    }
}
