package com.megaease.template.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LanguageHelper {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String msg, Object... parameters) {
        return messageSource.getMessage(msg, parameters, msg, LocaleContextHolder.getLocale());
    }
}
