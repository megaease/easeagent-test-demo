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
package com.megaease.easeagent.demo.employee.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    static Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @GetMapping("/message")
    public String test(@RequestHeader MultiValueMap<String, String> headers) {
        System.out.println("---------------- headers begin");
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            System.out.println(String.format("key: %s value: %s", entry.getKey(), entry.getValue()));
        }
        System.out.println("---------------- headers end");
        log.info("Gateway Called in employee Service");
        return "Gateway Called in employee Service";
    }
}

