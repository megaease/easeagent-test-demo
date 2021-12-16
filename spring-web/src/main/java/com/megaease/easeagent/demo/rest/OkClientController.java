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

package com.megaease.easeagent.demo.rest;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@RestController
public class OkClientController {
    private final AtomicLong counter = new AtomicLong();
    OkHttpClient client = new OkHttpClient();

    @Value("${server.port}")
    int port;


    @GetMapping("/okclient")
    public String client() throws IOException {
        final String uri = "http://127.0.0.1:" + port + "/hello";
        Request request = new Request.Builder()
                .url(uri)
                .build();

        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            return String.format("count : %s  hello : %s", counter.incrementAndGet(), response.body().string());
        }
    }

    @GetMapping("/okclient_async")
    public String clientAsync() throws IOException, InterruptedException {
        final String uri = "http://127.0.0.1:" + port + "/hello";
        Request request = new Request.Builder()
                .url(uri)
                .build();

        Call call = client.newCall(request);
        final AtomicReference<String> reference = new AtomicReference<>();
        Callback callback = new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                synchronized (reference) {
                    reference.set(e.getMessage());
                    reference.notifyAll();
                }
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                synchronized (reference) {
                    reference.set(String.format("count : %s  hello : %s", counter.incrementAndGet(), response.body().string()));
                    reference.notifyAll();
                }
            }
        };
        call.enqueue(callback);
        synchronized (reference) {
            if (reference.get() == null) {
                reference.wait();
            }
        }
        return reference.get();
    }

}
