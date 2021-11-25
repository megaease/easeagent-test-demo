package com.megaease.template.common.exception;


import com.megaease.template.common.enums.BusinessError;

public class ResourceNotFoundException extends BusinessException {

    public ResourceNotFoundException(String message) {
        this(message, (Object[]) null);
    }

    public ResourceNotFoundException(String message, Object... parameters) {
        super(message, BusinessError.RESOURCE_NOT_EXISTS, parameters);
    }
}
