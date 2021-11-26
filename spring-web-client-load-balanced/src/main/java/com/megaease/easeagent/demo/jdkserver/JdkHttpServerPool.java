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

package com.megaease.easeagent.demo.jdkserver;

import java.io.IOException;
import java.net.DatagramSocket;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

public class JdkHttpServerPool {
    public static ConcurrentHashMap<Integer, JdkHttpServer> servers = new ConcurrentHashMap<>();

    public static int startOne() {
        try {
            DatagramSocket s = new DatagramSocket(0);
            int port = s.getLocalPort();
            System.out.println(String.format("----- start http server at port: %s----------", port));
            JdkHttpServer server = new JdkHttpServer((a) -> {
                a.getResponseHeaders().add("X-EG-Circuit-Breaker", "bbbb");
            }, (a, b) -> "", port);
            servers.put(port, server);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        server.startHttpServer();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            return port;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void init(int count) {
        while (servers.size() < count) {
            synchronized (servers) {
                if (servers.size() < count) {
                    startOne();
                }
            }
        }
    }

    public static int[] ports() {
        int count = servers.size();
        int[] ports = new int[count];
        Enumeration<Integer> enumeration = servers.keys();
        for (int i = 0; i < count && enumeration.hasMoreElements(); i++) {
            ports[i] = enumeration.nextElement();
        }
        return ports;
    }
}
