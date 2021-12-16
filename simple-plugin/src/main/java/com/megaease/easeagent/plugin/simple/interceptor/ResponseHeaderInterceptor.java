/*
 * Copyright (c) 2021, MegaEase
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.megaease.easeagent.plugin.simple.interceptor;

import com.megaease.easeagent.plugin.Interceptor;
import com.megaease.easeagent.plugin.MethodInfo;
import com.megaease.easeagent.plugin.annotation.AdviceTo;
import com.megaease.easeagent.plugin.api.Context;
import com.megaease.easeagent.plugin.api.config.ConfigConst;
import com.megaease.easeagent.plugin.bridge.EaseAgent;
import com.megaease.easeagent.plugin.enums.Order;
import com.megaease.easeagent.plugin.simple.SimplePlugin;
import com.megaease.easeagent.plugin.simple.points.DoFilterPoints;

import javax.servlet.http.HttpServletResponse;

@AdviceTo(value = DoFilterPoints.class, plugin = SimplePlugin.class)
public class ResponseHeaderInterceptor implements Interceptor {
    private Object START_KEY = new Object();
    @Override
    public void before(MethodInfo methodInfo, Context context) {
        context.put(START_KEY, System.currentTimeMillis());
    }

    @Override
    public void after(MethodInfo methodInfo, Context context) {
        Long start = context.get(START_KEY);
        HttpServletResponse httpServletResponse = (HttpServletResponse) methodInfo.getArgs()[1];
        String serviceName = EaseAgent.getConfig(ConfigConst.SERVICE_NAME);
        httpServletResponse.setHeader("easeagent-srv-name", serviceName);
        httpServletResponse.setHeader("easeagent-start-timestamp", start.toString());
    }

    // Order.TRACING.getName();
    @Override
    public String getType() {
        return "tracing";
    }

    // Order.TRACING.getOrder();
    @Override
    public int order() {
        return 100;
    }
}

