package com.megaease.easeagent.sofarpc.client.impl;

import com.alipay.sofa.rpc.common.RpcConstants;
import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.megaease.easeagent.sofarpc.api.AService;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@SofaService(interfaceType = AService.class,
		bindings = {@SofaServiceBinding(bindingType = RpcConstants.PROTOCOL_TYPE_H2C, timeout = 1000),
				@SofaServiceBinding(bindingType = "dubbo", timeout = 1000),
				@SofaServiceBinding(bindingType = RpcConstants.PROTOCOL_TYPE_REST, timeout = 1000)})
public class AServiceImpl implements AService {

	@Override
	public String sayHi(String name) {
		if ("timeout".equals(name)) {
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException ignored) {
			}
		}
		if ("error".equals(name)) {
			throw new RuntimeException("client call exception");
		}
		return String.format("the client say hi: %s", name);
	}
}
