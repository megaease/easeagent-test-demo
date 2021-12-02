package com.megaease.easeagent.demo.employee.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@GetMapping("/message")
	public String test() {
		return "Gateway Called in employee Service";
	}
}

