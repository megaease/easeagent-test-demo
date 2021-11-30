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

package com.megaease.easeagent.demo.httpclient;

import com.megaease.easeagent.demo.httpclient.httpserver.JdkHttpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class HttpClientApplication {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                new JdkHttpServer((a) -> {
                    a.getResponseHeaders().add("X-EG-Circuit-Breaker", "bbbb");
                }, (a, b) -> "").startHttpServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        SpringApplication.run(HttpClientApplication.class, args);
    }
}
