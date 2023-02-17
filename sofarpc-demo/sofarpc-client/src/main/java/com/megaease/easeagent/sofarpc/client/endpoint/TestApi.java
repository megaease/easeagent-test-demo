package com.megaease.easeagent.sofarpc.client.endpoint;

import com.alipay.sofa.rpc.api.future.SofaResponseFuture;
import com.alipay.sofa.rpc.common.RpcConstants;
import com.alipay.sofa.rpc.context.RpcInvokeContext;
import com.alipay.sofa.rpc.core.exception.SofaRpcException;
import com.alipay.sofa.rpc.core.invoke.SofaResponseCallback;
import com.alipay.sofa.rpc.core.request.RequestBase;
import com.alipay.sofa.rpc.message.bolt.BoltResponseFuture;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.megaease.easeagent.sofarpc.api.BService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.ExecutionException;

@Path("/test")
@Component
public class TestApi {
	private static final Logger LOG = LoggerFactory.getLogger(TestApi.class);
	@SofaReference(interfaceType = BService.class,
			binding = @SofaReferenceBinding(bindingType = RpcConstants.PROTOCOL_TYPE_BOLT, timeout = 1000, invokeType = RpcConstants.INVOKER_TYPE_SYNC, retries = 1))
	private BService bService;
	@SofaReference(interfaceType = BService.class,
			binding = @SofaReferenceBinding(bindingType = RpcConstants.PROTOCOL_TYPE_BOLT, timeout = 1000, invokeType = RpcConstants.INVOKER_TYPE_FUTURE))
	private BService asyncBService;
	@SofaReference(interfaceType = BService.class,
			binding = @SofaReferenceBinding(bindingType = RpcConstants.PROTOCOL_TYPE_BOLT, timeout = 1000, invokeType = RpcConstants.INVOKER_TYPE_CALLBACK, retries = 1))
	private BService callbackBService;
	@SofaReference(interfaceType = BService.class,
			binding = @SofaReferenceBinding(bindingType = RpcConstants.PROTOCOL_TYPE_BOLT, timeout = 1000, invokeType = RpcConstants.INVOKER_TYPE_ONEWAY))
	private BService onewayBService;

	@GET
	@Path(value = "/{invokeType}/{protocol}/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public String syncInvoke(@PathParam("invokeType") String invokeType, @PathParam("protocol") String protocol, @PathParam("name") String name) {
		String sayHelloMessage = null;
		if (RpcConstants.INVOKER_TYPE_SYNC.equals(invokeType) || "chain".equals(invokeType)) {
			sayHelloMessage = bService.sayHi(invokeType, protocol, name);
		}
		if (RpcConstants.INVOKER_TYPE_FUTURE.equals(invokeType)) {
			asyncBService.sayHi(invokeType, protocol, name);
			BoltResponseFuture boltResponseFuture = (BoltResponseFuture) SofaResponseFuture.getFuture();
			try {
				sayHelloMessage = (String) boltResponseFuture.get();
			} catch (InterruptedException | ExecutionException e) {
				throw new RuntimeException(e);
			}
		}
		if (RpcConstants.INVOKER_TYPE_CALLBACK.equals(invokeType)) {
			sayHelloMessage = callbackBService.sayHi(invokeType, protocol, name);
			RpcInvokeContext.getContext().setResponseCallback(getResponseCallback());
		}
		if (RpcConstants.INVOKER_TYPE_ONEWAY.equals(invokeType)) {
			onewayBService.sayHi(invokeType, protocol, name);
		}
		return sayHelloMessage;
	}

	private static SofaResponseCallback<Object> getResponseCallback() {
		return new SofaResponseCallback<Object>() {
			@Override
			public void onAppResponse(Object appResponse, String methodName, RequestBase request) {
				LOG.info("{} invoke get result: {}", methodName, appResponse);
			}

			@Override
			public void onAppException(Throwable throwable, String methodName, RequestBase request) {
				LOG.info("{} invoke get exception: {}", methodName, throwable.getMessage());
			}

			@Override
			public void onSofaException(SofaRpcException sofaException, String methodName, RequestBase request) {
				LOG.info("{} invoke get exception: {}", methodName, sofaException.getMessage());
			}
		};
	}

}
