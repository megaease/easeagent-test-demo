package com.megaease.template.common.exception;

import com.megaease.template.common.enums.BusinessError;
import org.springframework.validation.BindingResult;

public class BeanValidationException extends BusinessException {

    private static final long serialVersionUID = 2328912752148709L;

    private BindingResult bindingResult;

    public BeanValidationException(String msg) {
        super(msg);
    }

    public BeanValidationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BeanValidationException(String msg, Throwable cause, Object... params) {
        super(msg, cause, params);
    }

    public BeanValidationException(String message,
                                   BusinessError businessError, Object... parameters) {
        super(message, businessError, parameters);
    }

    public BeanValidationException(String message,
                                   BusinessError businessError, BindingResult bindingResult, Object... parameters) {
        super(message, businessError, parameters);
    }

    public BeanValidationException(String code, BindingResult bindingResult, Object... parameters) {
        super(code, BusinessError.DATA_VALIDATION_FAILED, parameters);
        this.bindingResult = bindingResult;
    }

    public BeanValidationException(String code, Throwable throwable, BindingResult bindingResult,
                                   Object... parameters) {
        super(code, BusinessError.DATA_VALIDATION_FAILED, throwable, parameters);
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}