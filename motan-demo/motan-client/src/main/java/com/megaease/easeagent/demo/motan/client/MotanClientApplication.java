package com.megaease.easeagent.demo.motan.client;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.config.springsupport.*;
import com.weibo.api.motan.util.MotanSwitcherUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MotanClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(MotanClientApplication.class, args);
        MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
    }

    @Bean
    public AnnotationBean motanAnnotationBean() {
        AnnotationBean motanAnnotationBean = new AnnotationBean();
        motanAnnotationBean.setPackage("com.megaease.easeagent.demo.motan.client");
        return motanAnnotationBean;
    }

    @Bean(name = "motan")
    public ProtocolConfigBean protocolConfig() {
        ProtocolConfigBean config = new ProtocolConfigBean();
        config.setDefault(true);
        config.setName("motan");
        config.setMaxContentLength(1048576);
        return config;
    }

    @Bean(name = "registry")
    public RegistryConfigBean registryConfigBean() {
        RegistryConfigBean config = new RegistryConfigBean();
        config.setRegProtocol("zookeeper");
        config.setAddress("127.0.0.1:2181");
        config.setConnectTimeout(5000);
        return config;
    }

    @Bean(name = "basicServiceConfig")
    public BasicServiceConfigBean basicServiceConfig() {
        BasicServiceConfigBean basicServiceConfig = new BasicServiceConfigBean();
        basicServiceConfig.setCheck(false);
        basicServiceConfig.setExport("motan:8002");
        basicServiceConfig.setRegistry("registry");
        basicServiceConfig.setApplication("motan-client");
        basicServiceConfig.setGroup("motan-client-group");
        basicServiceConfig.setModule("motan-client-module");
        basicServiceConfig.setVersion("1.0.0");
        basicServiceConfig.setRequestTimeout(3000);
        return basicServiceConfig;
    }

    @Bean(name = "basicRefererConfig")
    public BasicRefererConfigBean basicRefererConfig() {
        BasicRefererConfigBean basicRefererConfig = new BasicRefererConfigBean();
        basicRefererConfig.setVersion("1.0.0");
        basicRefererConfig.setGroup("motan-server-group");
        basicRefererConfig.setModule("motan-server-module");
        basicRefererConfig.setApplication("motan-server");
        basicRefererConfig.setProtocol("motan");
        basicRefererConfig.setRegistry("registry");
        basicRefererConfig.setCheck(false);
        basicRefererConfig.setAccessLog(false);
        basicRefererConfig.setRetries(0);
        basicRefererConfig.setThrowException(true);
        return basicRefererConfig;
    }
}
