package com.megaease.easeagent.demo.dubbo.endpoint;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.megaease.easeagent.demo.dubbo.model.User;
import com.megaease.easeagent.demo.dubbo.service.TestAsyncService;
import com.megaease.easeagent.demo.dubbo.service.TestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/test")
public class TestEndpoint {

	@Reference(version = "1.0.0",
			group = "a",
			application = "${dubbo.application.id}",
			timeout = 1000,
			check = false
	)
	private TestService testService;


	@Reference(version = "1.0.0",
			group = "a",
			application = "${dubbo.application.id}",
			timeout = 5000,
			retries = 0,
			check = false
	)
	private TestService serverTimeoutService;

	@Reference(version = "1.0.0",
			group = "a",
			application = "${dubbo.application.id}",
			timeout = 1000,
			retries = 0,
			check = false
	)
	private TestService clientTimeoutService;

	@Reference(version = "1.0.0",
			group = "a",
			application = "${dubbo.application.id}",
			timeout = 3000,
			check = false,
			async = true
	)
	private TestAsyncService testAsyncService;

	@RequestMapping("/async/{userName}")
	@ResponseBody
	public ResponseEntity<User> asyncTest(@PathVariable(value = "userName") String userName) throws ExecutionException, InterruptedException {
		testAsyncService.getUser(userName);
		Future<User> future = RpcContext.getContext().getFuture();
		return ResponseEntity.ok(future.get());
	}

	@RequestMapping("/void")
	@ResponseBody
	public ResponseEntity<Void> test() {
		testService.testNoReturnValueAndNoArgs();
		return ResponseEntity.ok().build();
	}

	@RequestMapping("/sync/{userName}")
	@ResponseBody
	public ResponseEntity<User> getUser(@PathVariable(value = "userName") String userName) throws InterruptedException {
		User user = testService.getUser(userName, false);
		return ResponseEntity.ok(user);
	}

	@RequestMapping("/timeout/{side}")
	@ResponseBody
	public ResponseEntity<User> timeoutTest(@PathVariable("side") String side) throws InterruptedException {
		User user = null;
		if (side.equals("server")) {
			user = serverTimeoutService.getUser("serverTimeout", true);
		}
		if(side.equals("client")) {
			user = clientTimeoutService.getUser("clientTimeout", true);
		} else {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(user);
	}
}

