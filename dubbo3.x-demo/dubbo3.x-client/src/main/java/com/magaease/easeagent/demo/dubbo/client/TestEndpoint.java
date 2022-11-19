package com.magaease.easeagent.demo.dubbo.client;

import com.magaease.easeagent.demo.dubbo.model.Message;
import com.magaease.easeagent.demo.dubbo.service.AService;
import com.magaease.easeagent.demo.dubbo.service.BService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/test")
public class TestEndpoint {
    @DubboReference(group = "dubbo-server", timeout = 3000, retries = 0)
    private AService aService;

    @DubboReference(group = "dubbo-server", timeout = 1000, retries = 0)
    private AService clientTimeoutAService;

    @DubboReference(group = "dubbo-server", timeout = 5000, retries = 0)
    private AService serverTimeoutAService;

    @DubboReference(group = "dubbo-server", timeout = 2000, retries = 0)
    private BService bService;

    @GetMapping("/sync/{name}")
    public ResponseEntity<Message> syncTest(@PathVariable("name") String name) {
        Message message = aService.sayHello(name, false);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/timeout/{side}")
    public ResponseEntity<Message> timeoutTest(@PathVariable("side") String side) {
        Message result;
        if (side.equals("client")) {
            result = clientTimeoutAService.sayHello(side, true);
        } else if (side.equals("server")) {
            result = serverTimeoutAService.sayHello(side, true);
        } else {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/void")
    public ResponseEntity<Void> voidTest() {
        bService.voidTest();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/chain/{name}")
    public ResponseEntity<String> chainTest(@PathVariable("name") String name) {
        String result = bService.test(name);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/async/{name}")
    public ResponseEntity<Message> asyncTest(@PathVariable("name") String name) throws ExecutionException, InterruptedException {
        CompletableFuture<Message> result = aService.sayHelloAsync(name);
        return ResponseEntity.ok(result.get());
    }
}
