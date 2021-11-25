package com.megaease.template.common.exception;


import com.megaease.template.common.enums.BusinessError;

public class ClientParamException extends BusinessException {


    public ClientParamException(String msg) {
        super(msg, BusinessError.CLIENT_PARAM_ERR);
    }

    public ClientParamException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ClientParamException(String msg, Throwable cause, Object... params) {
        super(msg, cause, params);
    }

    public ClientParamException(String message, BusinessError businessError,
                                Object... parameters) {
        super(message, businessError, parameters);
    }

    public ClientParamException(String message, Object... parameters) {
        super(message, BusinessError.CLIENT_PARAM_ERR, parameters);
    }
}
