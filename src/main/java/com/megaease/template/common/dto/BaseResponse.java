package com.megaease.template.common.dto;


import com.megaease.template.common.enums.CustomHttpStatus;

import java.util.Date;

public class BaseResponse {

    private CustomHttpStatus status;

    private String error;

    private Integer errorCode;

    private String message;

    private Date timestamp = new Date();

    private String path;

    private String trace;

    private BaseResponse(Builder builder) {
        setStatus(builder.status);
        setErrorCode(builder.errorCode);
        setMessage(builder.message);
        setError(builder.error);
        setPath(builder.path);
        setTrace(builder.trace);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public CustomHttpStatus getStatus() {
        return status;
    }

    public void setStatus(CustomHttpStatus status) {
        this.status = status;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public static final class Builder {

        private CustomHttpStatus status;
        private Integer errorCode;
        private String message;
        private String error;
        private String path;
        private String trace;

        private Builder() {
        }

        public Builder status(CustomHttpStatus status) {
            this.status = status;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public Builder errorCode(Integer code) {
            this.errorCode = code;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder trace(String trace) {
            this.trace = trace;
            return this;
        }

        public BaseResponse build() {
            return new BaseResponse(this);
        }
    }
}
