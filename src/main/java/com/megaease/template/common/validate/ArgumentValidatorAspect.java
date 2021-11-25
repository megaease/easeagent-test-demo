package com.megaease.template.common.validate;

import com.megaease.template.common.utils.BeanValidator;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

@Aspect
@Component
@Slf4j
public class ArgumentValidatorAspect {

    @Pointcut("within(@com.megaease.template.common.validate.Validation *)")
    public void validationClassMethods() {
    }

    @Before("validationClassMethods()")
    public void validateArguments(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] arguments = joinPoint.getArgs();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (Objects.nonNull(parameter.getAnnotation(Valid.class))
                    || Objects.nonNull(parameter.getAnnotation(Validated.class))) {
                Object argument = arguments[i];
                BeanValidator.check(argument);
            }
        }
    }
}
