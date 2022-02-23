package com.megaease.easeagent.plugin.simple.points;

import com.megaease.easeagent.plugin.Points;
import com.megaease.easeagent.plugin.matcher.ClassMatcher;
import com.megaease.easeagent.plugin.matcher.IClassMatcher;
import com.megaease.easeagent.plugin.matcher.IMethodMatcher;
import com.megaease.easeagent.plugin.matcher.MethodMatcher;

import java.util.Set;

public class ResponseProcessorPoints implements Points {
    @Override
    public IClassMatcher getClassMatcher() {
        return ClassMatcher.builder()
                .hasClassName("org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor")
                .build();
    }

    @Override
    public Set<IMethodMatcher> getMethodMatcher() {
        return MethodMatcher.builder().named("handleReturnValue").build().toSet();
    }
}
