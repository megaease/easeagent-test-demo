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

package com.megaease.easeagent.demo.httpclient.httpserver;

import com.sun.net.httpserver.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class JdkHttpServer {
    private final Consumer<HttpExchange> beforeF;
    private final BiFunction<HttpExchange, String, String> afterF;

    public JdkHttpServer(Consumer<HttpExchange> beforeF, BiFunction<HttpExchange, String, String> afterF) {
        this.beforeF = beforeF;
        this.afterF = afterF;
    }


    public void startHttpServer() throws IOException {
        int port = 8500;
        System.out.println("start http server port: " + port);
        HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);
        HttpContext context = server.createContext("/example");
        context.setHandler(JdkHttpServer.this::handleRequest);
        server.start();
    }

    public void handleRequest(HttpExchange exchange) throws IOException {
        beforeF.accept(exchange);
        URI requestURI = exchange.getRequestURI();
        printRequestInfo(exchange);
        String response = "This is the response at " + requestURI;
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
        afterF.apply(exchange, response);
    }

    public void printRequestInfo(HttpExchange exchange) {
        System.out.println("-- headers --");
        Headers requestHeaders = exchange.getRequestHeaders();
        requestHeaders.entrySet().forEach(System.out::println);

        System.out.println("-- principle --");
        HttpPrincipal principal = exchange.getPrincipal();
        System.out.println(principal);

        System.out.println("-- HTTP method --");
        String requestMethod = exchange.getRequestMethod();
        System.out.println(requestMethod);

        System.out.println("-- query --");
        URI requestURI = exchange.getRequestURI();
        String query = requestURI.getQuery();
        System.out.println(query);
    }

    public static void main(String[] args) throws IOException {
        new JdkHttpServer((a) -> {
            a.getResponseHeaders().add("X-EG-Circuit-Breaker", "bbbb");
        }, (a, b) -> "").startHttpServer();
    }
}
