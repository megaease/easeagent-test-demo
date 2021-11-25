package com.megaease.template.common.exception;

import com.megaease.template.common.dto.BaseResponse;
import com.megaease.template.common.enums.BusinessError;
import com.megaease.template.common.enums.CustomHttpStatus;
import com.megaease.template.common.utils.LanguageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final Integer ARGUMENT_NOT_VALID_CODE = 100005;
    @Autowired
    private LanguageHelper languageHelper;
    @Autowired
    private HttpServletRequest request;

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<BaseResponse> process(Throwable exception) {
        loggerException(exception);
        String message = languageHelper.getMessage("system.error");
        BaseResponse baseResponse = BaseResponse.newBuilder()
                .error(BusinessError.GENERAL_SERVER_ERR.getAlias())
                .errorCode(BusinessError.GENERAL_SERVER_ERR.getCode())
                .status(CustomHttpStatus.INTERNAL_SERVER_ERROR)
                .path(request.getServletPath())
                .message(message).build();
        return ResponseEntity.status(baseResponse.getStatus().value()).body(baseResponse);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse> process(BusinessException exception) {
        return generateBaseResponse(exception);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<BaseResponse> process(AuthenticationException exception) {
        BusinessException result = new BusinessException(
                exception.getMessage(),
                BusinessError.UNAUTHORIZED
        );
        return generateBaseResponse(result);
    }

    @ExceptionHandler(BeanValidationException.class)
    public ResponseEntity<BaseResponse> process(BeanValidationException exception) {
        if (exception.getBindingResult().getErrorCount() > 0) {
            List<ObjectError> list = exception.getBindingResult().getAllErrors();
            StringBuilder sb = new StringBuilder();
            for (ObjectError tmp : list) {
                if (tmp instanceof FieldError) {
                    FieldError fieldError = (FieldError) tmp;
                    sb.append(fieldError.getField())
                            .append(": ")
                            .append(languageHelper.getMessage(
                                    fieldError.getDefaultMessage(),
                                    fieldError.getArguments()
                            ))
                            .append("\n");
                }
            }
            return generateBaseResponse(new ClientParamException(sb.toString()));
        }
        ClientParamException e = new ClientParamException("clinet params", exception);
        loggerException(e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    private void loggerException(Throwable e) {
        LOGGER.error("exception process", e);
    }

    private ResponseEntity<BaseResponse> generateBaseResponse(BusinessException exception) {
        String message = languageHelper
                .getMessage(exception.getMessage(), exception.getParameters());
        BaseResponse baseResponse = BaseResponse.newBuilder()
                .error(exception.getBusinessError().getAlias())
                .errorCode(exception.getBusinessError().getCode())
                .status(exception.getBusinessError().getHttpStatus())
                .message(message)
                .path(request.getServletPath())
                .build();
        return ResponseEntity.status(baseResponse.getStatus().value()).body(baseResponse);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        BaseResponse baseResponse = BaseResponse.newBuilder()
                .error(status.getReasonPhrase())
                .status(CustomHttpStatus.parse(status.value()))
                .message(ex.getMessage())
                .path(this.request.getServletPath())
                .build();
        return super.handleExceptionInternal(ex, baseResponse, headers, status, request);
    }
}


