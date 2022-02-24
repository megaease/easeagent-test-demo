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
 *
 */
package com.baeldung.setup;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Setup {

    private static final String ORDERS_QUEUE_NAME = "orders-queue";
    private static final String ORDERS_EXCHANGE_NAME = "orders-direct-exchange";
    private static final String ORDERS_ALTERNATE_EXCHANGE_NAME = "orders-alternate-exchange";
    private static final String ORDERS_ROUTING_KEY = "orders-routing-key";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        Map<String, Object> exchangeArguments = new HashMap<>();
        exchangeArguments.put("alternate-exchange", ORDERS_ALTERNATE_EXCHANGE_NAME);
        channel.exchangeDeclare(ORDERS_EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true, false, exchangeArguments);

        Map<String, Object> queueArguments = new HashMap<>();
        queueArguments.put("x-message-ttl", 60000);
        queueArguments.put("x-max-priority", 10);
        channel.queueDeclare(ORDERS_QUEUE_NAME, true, false, false, queueArguments);

        channel.queueBind(ORDERS_QUEUE_NAME, ORDERS_EXCHANGE_NAME, ORDERS_ROUTING_KEY);
    }

}
