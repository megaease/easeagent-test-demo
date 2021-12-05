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

package com.megaease.easeagent.demo.kafka;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class KafkaController {
    String topic = "mytopic";
    Producer<String, String> producer = KafkaProducerFactory.create();


    @GetMapping("/kafka/producer")
    public String producer() {
        for (int i = 1; i <= 1; i++) {
            String value = "value_" + i;
            ProducerRecord<String, String> msg = new ProducerRecord<String, String>(topic, value);
            producer.send(msg);
        }
        //列出topic的相关信息
        List<PartitionInfo> partitions = new ArrayList<PartitionInfo>();
        partitions = producer.partitionsFor(topic);
        for (PartitionInfo p : partitions) {
            System.out.println(p);
        }

        return "send message over.";
    }


    @GetMapping("/kafka/producer_async")
    public String producerAsync() {
        for (int i = 1; i <= 1; i++) {
            String value = "value_" + i;
            ProducerRecord<String, String> msg = new ProducerRecord<String, String>(topic, value);
            producer.send(msg, (metadata, exception) -> System.out.println("topic : " + metadata.topic()));
        }
        //列出topic的相关信息
        List<PartitionInfo> partitions = new ArrayList<PartitionInfo>();
        partitions = producer.partitionsFor(topic);
        for (PartitionInfo p : partitions) {
            System.out.println(p);
        }

        return "send message over.";
    }
}
