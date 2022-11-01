package com.megaease.easeagent.demo.motan.client;

import com.megaease.easeagent.demo.motan.api.model.User;
import com.megaease.easeagent.demo.motan.api.service.AService;
import com.megaease.easeagent.demo.motan.api.service.BServiceAsync;
import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestEndpoint {

    @MotanReferer(basicReferer = "basicRefererConfig")
    private AService aService;

    @MotanReferer(basicReferer = "basicRefererConfig", requestTimeout = 1000)
    private AService clientTimeoutService;


    @MotanReferer(basicReferer = "basicRefererConfig", requestTimeout = 3000)
    private AService serverTimeoutService;

    @MotanReferer(basicReferer = "basicRefererConfig", async = true)
    private BServiceAsync bServiceAsync;

    @RequestMapping("/sync/{name}")
    @ResponseBody
    public ResponseEntity<String> test(@PathVariable("name") String name) {
        User user = new User();
        user.setName(name);
        String greetMsg = aService.hello(user, false);
        return ResponseEntity.ok(greetMsg);
    }

    @RequestMapping("/timeout/{side}")
    @ResponseBody
    public ResponseEntity<String> timeoutTest(@PathVariable("side") String side) {
        User user = new User();
        user.setName(side);
        String greetMsg = null;
        if (side.equals("client")) {
            greetMsg = clientTimeoutService.hello(user, true);
        } else if (side.equals("server")) {
            greetMsg = serverTimeoutService.hello(user, true);
        } else {
            return ResponseEntity.badRequest().body("parameter error");
        }
        return ResponseEntity.ok(greetMsg);
    }

    @RequestMapping("/async/{name}")
    @ResponseBody
    public ResponseEntity<String> asyncTest(@PathVariable("name") String name) {
        String greetMsg = (String) bServiceAsync.helloAsync(name).getValue();
        return ResponseEntity.ok(greetMsg);
    }

    @RequestMapping("/void")
    @ResponseBody
    public void test() {
        aService.testNoReturnValue();
    }
}
