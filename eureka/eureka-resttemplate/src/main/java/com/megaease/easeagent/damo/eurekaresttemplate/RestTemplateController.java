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

package com.megaease.easeagent.damo.eurekaresttemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class RestTemplateController {
    private final AtomicLong counter = new AtomicLong();
    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateController.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/rest_template_url")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        LOGGER.info("rest_template name: {}", name);


        RestTemplate template = new RestTemplate();
        return template.getForObject("http://127.0.0.1:8081/registers/greeting?name=" + name, String.class);
    }

    @GetMapping("/rest_template_lbc")
    public String greetingLBC(@RequestParam(value = "name", defaultValue = "World") String name) {
        LOGGER.info("rest_template name: {}", name);

        // PRODUCT 对应注册Eureka中的服务名字
        return restTemplate.getForObject("http://REGISTERS/registers/greeting?name=" + name, String.class);
    }
}
