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

package com.megaease.easeagent.demo.elasticsearch;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class ElasticsearchController {
    private final AtomicLong counter = new AtomicLong();
    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchController.class.getName());
    @Autowired
    private DamoRepository damoRepository;

    @Resource
    private RestHighLevelClient restHighLevelClient;


    @GetMapping("/elasticsearch_create")
    public Damo create(@RequestParam(value = "name", defaultValue = "World") String name, @RequestParam(value = "age", defaultValue = "0") Double age) {
        LOGGER.info("damo name: {}, age: {}", name, age);
        Damo damo = new Damo();
        damo.setName(name);
        damo.setAge(age);
        damo.setDec(String.format("hello %s! increment: %s", counter.incrementAndGet(), name));
        Damo newD = damoRepository.save(damo);
        return newD;
    }

    @GetMapping("/es-create-async")
    public void createAsync() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("name", "akwei");
        IndexRequest indexRequest = new IndexRequest("local-data").id("1").source(dataMap);
        this.restHighLevelClient.indexAsync(indexRequest, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                LOGGER.info("onResponse:{}", indexResponse.toString());
            }

            @Override
            public void onFailure(Exception e) {
                LOGGER.info("onFailure:", e);
            }
        });
    }
}
