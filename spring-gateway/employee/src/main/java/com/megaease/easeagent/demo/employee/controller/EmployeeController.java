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

