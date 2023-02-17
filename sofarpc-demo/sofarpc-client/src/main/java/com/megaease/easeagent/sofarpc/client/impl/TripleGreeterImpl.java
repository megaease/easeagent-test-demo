package com.megaease.easeagent.sofarpc.client.impl;

import com.alipay.sofa.rpc.common.RpcConstants;
import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.megaease.easeagent.sofarpc.proto.HelloReply;
import com.megaease.easeagent.sofarpc.proto.HelloRequest;
import com.megaease.easeagent.sofarpc.proto.SofaGreeterTriple;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@SofaService(interfaceType = SofaGreeterTriple.IGreeter.class,
		bindings = {@SofaServiceBinding(bindingType = RpcConstants.PROTOCOL_TYPE_TRIPLE,timeout = 1000)})
public class TripleGreeterImpl extends SofaGreeterTriple.GreeterImplBase {
	@Override
	public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
		if (request.getName().equals("error")) {
			throw new RuntimeException("call error");
		}
		if (request.getName().equals("timeout")) {
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException ignored) {
			}
			return;
		}
		HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + request.getName()).build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}
}
