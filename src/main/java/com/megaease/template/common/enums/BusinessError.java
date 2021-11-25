package com.megaease.template.common.enums;

public enum BusinessError {
    /**
     * 业务错误编码
     */

    CLIENT_PARAM_ERR("Bad Request", CustomHttpStatus.BAD_REQUEST, 40000001),
    DATA_VALIDATION_FAILED("Bean Validate Error", CustomHttpStatus.BAD_REQUEST, 40000002),
    DATA_DUPLICATE_CHECKED_ERR("Resource Exist", CustomHttpStatus.BAD_REQUEST, 40000003),
    RESOURCE_NOT_EXISTS("Resource Not Found", CustomHttpStatus.BAD_REQUEST, 40000004),
    RELATED_RESOURCE_EXISTS("Resource", CustomHttpStatus.BAD_REQUEST, 40000005),

    UNAUTHORIZED("Unauthorized", CustomHttpStatus.UNAUTHORIZED, 40100000),

    GENERAL_FORBID_ERR("Forbidden", CustomHttpStatus.FORBIDDEN, 40300001),

    GENERAL_SERVER_ERR("Internal Server Error", CustomHttpStatus.INTERNAL_SERVER_ERROR, 50000000),
    GENERAL_CREATE_ERR("Create Error", CustomHttpStatus.INTERNAL_SERVER_ERROR, 50000001),
    GENERAL_UPDATE_ERR("Update Error", CustomHttpStatus.INTERNAL_SERVER_ERROR, 50000002),
    GENERAL_DELETE_ERR("Delete Error", CustomHttpStatus.INTERNAL_SERVER_ERROR, 50000003),
    GENERAL_QUERY_ERR("Query Error", CustomHttpStatus.INTERNAL_SERVER_ERROR, 50000004);

    private String alias;

    private CustomHttpStatus httpStatus;

    private int code;

    BusinessError(String alias, CustomHttpStatus httpStatus, int code) {
        this.alias = alias;
        this.httpStatus = httpStatus;
        this.code = code;
    }

    public String getAlias() {
        return alias;
    }

    public CustomHttpStatus getHttpStatus() {
        return httpStatus;
    }

    public int getCode() {
        return code;
    }
}
