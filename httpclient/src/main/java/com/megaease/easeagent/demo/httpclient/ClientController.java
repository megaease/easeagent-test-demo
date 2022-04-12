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

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ClientController {
    private final AtomicLong counter = new AtomicLong();
    private final CloseableHttpClient httpclient = HttpClients.createDefault();
    Logger alog = LoggerFactory.getLogger("app");

    @GetMapping("/client")
    public String client() {
        String context = null;

        try {
            // 创建httpget.
            HttpGet httpget = new HttpGet(ExampleUrl.url);
            System.out.println("executing request " + httpget.getURI());
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                System.out.println("--------------------------------------");
                // 打印响应状态
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    // 打印响应内容长度
                    System.out.println("Response content length: " + entity.getContentLength());
                    // 打印响应内容
                    context = EntityUtils.toString(entity);
                }
                System.out.println("------------------------------------");
                org.slf4j.MDC.put("testMDC", "bbb");
                alog.info("-------------------alog---");
                throw new NullPointerException();
            } catch(Exception e) {
                alog.info("test exception", e);
            } finally {
                org.slf4j.MDC.remove("testMDC");
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
//            try {
//                httpclient.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        return String.format("count : %s  hello : %s", counter.incrementAndGet(), context);
    }


//    @GetMapping("/client_async")
//    public Callable<Greeting> clientAsync() {
//        return () -> new Greeting(counter.incrementAndGet(), exampleClient.example());
//    }
}
