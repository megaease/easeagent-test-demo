package com.megaease.easeagent.sofarpc.service.impl;

import com.alipay.sofa.rpc.common.RpcConstants;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.megaease.easeagent.sofarpc.api.AService;
import com.megaease.easeagent.sofarpc.api.BService;
import com.megaease.easeagent.sofarpc.proto.HelloReply;
import com.megaease.easeagent.sofarpc.proto.HelloRequest;
import com.megaease.easeagent.sofarpc.proto.SofaGreeterTriple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.NotSupportedException;
import java.util.concurrent.TimeUnit;

@Component
@SofaService(interfaceType = BService.class,
		bindings = {@SofaServiceBinding(bindingType = RpcConstants.PROTOCOL_TYPE_BOLT)})
public class BServiceImpl implements BService {
	private static final Logger LOG = LoggerFactory.getLogger(BServiceImpl.class);

	@SofaReference(interfaceType = AService.class,
			binding = @SofaReferenceBinding(bindingType = RpcConstants.PROTOCOL_TYPE_H2C,
					timeout = 1000,
					invokeType = RpcConstants.INVOKER_TYPE_SYNC))
	private AService h2cAService;
	@SofaReference(interfaceType = AService.class,
			binding = @SofaReferenceBinding(bindingType = "dubbo",
					timeout = 1000,
					invokeType = RpcConstants.INVOKER_TYPE_SYNC))
	private AService dubboAService;
	@SofaReference(interfaceType = AService.class,
			binding = @SofaReferenceBinding(bindingType = RpcConstants.PROTOCOL_TYPE_REST,
					timeout = 1000,
					invokeType = RpcConstants.INVOKER_TYPE_SYNC))
	private AService restAService;
	@SofaReference(interfaceType = SofaGreeterTriple.IGreeter.class,
			binding = @SofaReferenceBinding(bindingType = RpcConstants.PROTOCOL_TYPE_TRIPLE,
					timeout = 3000,
					invokeType = RpcConstants.INVOKER_TYPE_SYNC))
	private SofaGreeterTriple.IGreeter iGreeter;

	@Override
	public String sayHi(String invokeType, String protocol, String name) {
		if ("chain".equals(invokeType)) {
			if (RpcConstants.PROTOCOL_TYPE_H2C.equals(protocol)) {
				return h2cAService.sayHi(name);
			}
			if (protocol.equals("dubbo")) {
				return dubboAService.sayHi(name);
			}
			if (RpcConstants.PROTOCOL_TYPE_REST.equals(protocol)) {
				return restAService.sayHi(name);
			}
			if (RpcConstants.PROTOCOL_TYPE_TRIPLE.equals(protocol)) {
				HelloReply helloReply = iGreeter.sayHello(HelloRequest.newBuilder().setName(name).build());
				return helloReply.getMessage();
			}
			throw new NotSupportedException(String.format("not support this %s protocol", protocol));
		}
		if ("timeout".equals(name)) {
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
			}
		}
		if ("error".equals(name)) {
			throw new RuntimeException("call exception");
		}
		return String.format("Say hi %s", name);
	}
}
