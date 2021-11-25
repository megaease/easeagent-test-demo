package com.megaease.template.common.exception;


import com.megaease.template.common.enums.BusinessError;

/**
 * 用于处理非编程性的，关于业务的逻辑异常
 */
public class BusinessException extends RuntimeException {
    protected BusinessError businessError;
    private Object parameters;

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BusinessException(String msg, Throwable cause, Object... params) {
        super(msg, cause);
        this.parameters = params;
    }

    public BusinessException(String message, BusinessError businessError) {
        super(message);
        this.businessError = businessError;
    }

    /**
     * @param message    异常信息
     * @param parameters parameters
     */
    public BusinessException(String message, BusinessError businessError, Object... parameters) {
        super(message);
        this.businessError = businessError;
        this.parameters = parameters;
    }

    public BusinessError getBusinessError() {
        return businessError;
    }

    public Object getParameters() {
        return parameters;
    }
}
