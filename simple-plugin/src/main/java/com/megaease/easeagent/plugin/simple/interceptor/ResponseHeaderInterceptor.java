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
import com.megaease.easeagent.plugin.simple.SimplePlugin;
import com.megaease.easeagent.plugin.simple.advice.DoFilterAdvice;

import javax.servlet.http.HttpServletResponse;

@AdviceTo(value = DoFilterAdvice.class, plugin = SimplePlugin.class)
public class ResponseHeaderInterceptor implements Interceptor {
    @Override
    public void before(MethodInfo methodInfo, Context context) {
    }

    @Override
    public void after(MethodInfo methodInfo, Context context) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) methodInfo.getArgs()[1];
        httpServletResponse.setHeader("easeagent", "simple-plugin");
    }
}

