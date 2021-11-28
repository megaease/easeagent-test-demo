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

package com.megaease.easeagent.damo.eurekawebclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class WebClientController {
    private final AtomicLong counter = new AtomicLong();
    private static final Logger LOGGER = LoggerFactory.getLogger(WebClientController.class.getName());


    @Autowired
    private WebClient.Builder loadBalancedWebClientBuilder;

    @GetMapping("/webclient_lbc")
    public Mono<String> greetingLBC(@RequestParam(value = "name", defaultValue = "World") String name) {
        LOGGER.info("rest_template name: {}", name);

        // PRODUCT 对应注册Eureka中的服务名字
        return loadBalancedWebClientBuilder.build()
                .get()
                .uri("http://REGISTERS/registers/greeting?name=" + name)
                .retrieve()
                .bodyToMono(String.class)
                .map(greeting -> String.format("%s, %s!", greeting, name));
    }
}
