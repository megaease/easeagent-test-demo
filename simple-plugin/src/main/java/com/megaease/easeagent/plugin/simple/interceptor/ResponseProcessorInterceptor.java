package com.megaease.easeagent.plugin.simple.interceptor;

import com.megaease.easeagent.plugin.annotation.AdviceTo;
import com.megaease.easeagent.plugin.api.Context;
import com.megaease.easeagent.plugin.api.config.ConfigConst;
import com.megaease.easeagent.plugin.api.context.ContextUtils;
import com.megaease.easeagent.plugin.bridge.EaseAgent;
import com.megaease.easeagent.plugin.interceptor.Interceptor;
import com.megaease.easeagent.plugin.interceptor.MethodInfo;
import com.megaease.easeagent.plugin.simple.SimplePlugin;
import com.megaease.easeagent.plugin.simple.points.ResponseProcessorPoints;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletResponse;

@AdviceTo(value = ResponseProcessorPoints.class, plugin = SimplePlugin.class)
public class ResponseProcessorInterceptor implements Interceptor {
    public static final Object startKey = ResponseHeaderInterceptor.startKey;

    @Override
    public void before(MethodInfo methodInfo, Context context) {
        NativeWebRequest request = (NativeWebRequest) methodInfo.getArgs()[3];
        HttpServletResponse resp = (HttpServletResponse)request.getNativeResponse();
        if (resp == null) {
            return;
        }
        resp.setHeader("easeagent-srv-name", EaseAgent.getConfig(ConfigConst.SERVICE_NAME));
        resp.setHeader("easeagent-duration", ContextUtils.getDuration(context, startKey).toString());
    }
}
