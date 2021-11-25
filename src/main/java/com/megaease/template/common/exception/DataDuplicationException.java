package com.megaease.template.common.exception;


import com.megaease.template.common.enums.BusinessError;

/**
 * 已存在一个会导致冲突的重复数据
 */
public class DataDuplicationException extends BusinessException {

    public DataDuplicationException(String message) {
        this(message, (Object[]) null);
    }

    /**
     * @param message    异常信息
     * @param parameters parameters
     */
    public DataDuplicationException(String message, Object... parameters) {
        super(message, BusinessError.DATA_DUPLICATE_CHECKED_ERR, parameters);
    }
}
