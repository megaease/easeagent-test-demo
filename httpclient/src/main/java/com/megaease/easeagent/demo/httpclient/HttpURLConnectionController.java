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


import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class HttpURLConnectionController {
    private final AtomicLong counter = new AtomicLong();
    private final CloseableHttpClient httpclient = HttpClients.createDefault();
    Logger alog = LoggerFactory.getLogger("app");

    @GetMapping("/urlconnection")
    public String client() {
        String context = null;

        try {
            // 创建httpget.
            URL url = new URL(ExampleUrl.url);
            HttpURLConnection conn = getConnection(url, "GET", null);
            // 执行get请求.
            int responseCode = conn.getResponseCode();
            try {
                InputStream is;
                if (responseCode == 200) {
                    is = conn.getInputStream();
                } else {
                    is = conn.getErrorStream();
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                reader.close();
                context = stringBuilder.toString();

            } catch (Exception e) {
                alog.info("test exception", e);
            } finally {
                org.slf4j.MDC.remove("testMDC");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.format("count : %s  hello : %s", counter.incrementAndGet(), context);
    }


    private HttpURLConnection getConnection(
            URL url, String method, Map<String, String> headers) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if (conn instanceof HttpsURLConnection) {
            HttpsURLConnection connHttps = (HttpsURLConnection) conn;
            try {
                SSLContext ctx = SSLContext.getInstance("TLS");
                ctx.init(new KeyManager[0], new TrustAllTrustManager[]{new TrustAllTrustManager()}, new SecureRandom());
                connHttps.setSSLSocketFactory(ctx.getSocketFactory());
                connHttps.setHostnameVerifier(new HostnameVerifier() {
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
            } catch (Exception e) {
                throw new IOException(e);
            }
            conn = connHttps;
        }

        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty(HttpHeaders.HOST, url.getHost());
        if (headers != null) {
            for (String key : headers.keySet()) {
                conn.setRequestProperty(key, headers.get(key));
            }
        }

        return conn;
    }

    private static class TrustAllTrustManager implements X509TrustManager {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
    }

}
