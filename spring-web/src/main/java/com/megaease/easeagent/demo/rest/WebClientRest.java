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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
public class WebClientRest {
    private static final Logger logger = LoggerFactory.getLogger(WebClientRest.class);

    @Value("${server.port}")
    int port;

    private void consume(String msg) {
        logger.info(msg);
    }

    @GetMapping("/web_client")
    public Flux<String> greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        System.out.println("Starting NON-BLOCKING Controller!");
        final String uri = "http://127.0.0.1:" + port + "/hello";
        Flux<String> tweetFlux = WebClient.create()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(String.class);

//        tweetFlux.subscribe(e -> consume(e));
        logger.info("Exiting NON-BLOCKING Controller!");
        return tweetFlux;
    }

    @RequestMapping("/hello")
    public String hello(@RequestHeader MultiValueMap<String, String> headers) {
        logger.info("---------------- headers begin");
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            logger.info("key: {} value: {}", entry.getKey(), entry.getValue());
        }
        logger.info("---------------- headers end");


        Random r = new Random();

        int delay = r.nextInt(1000);
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
        }

        String resp = "easeagent-" + System.currentTimeMillis();
        logger.info("hello:" + resp);
        return resp;
    }
}

