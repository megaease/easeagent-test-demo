package com.megaease.easeagent.demo.consumer.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

	@GetMapping("/message")
	public String test() {
		return "Gateway Called in consumer Service";
	}
}

