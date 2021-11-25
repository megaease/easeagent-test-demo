package com.megaease.template.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public interface BaseEnum {

    String DEFAULT_CODE_NAME = "code";

    String DEFAULT_VALUE_NAME = "value";

    static <T extends Enum<T>> T codeOfEnum(Class<T> enumClass, Integer code) {
        if (code == null)
            throw new IllegalArgumentException("DisplayedEnum value should not be null");
        if (enumClass.isAssignableFrom(BaseEnum.class))
            throw new IllegalArgumentException("illegal DisplayedEnum type");
        T[] enums = enumClass.getEnumConstants();
        for (T t : enums) {
            BaseEnum baseEnum = (BaseEnum) t;
            if (baseEnum.getCode().equals(code))
                return (T) baseEnum;
        }
        throw new IllegalArgumentException("cannot parse integer: " + code + " to " + enumClass.getName());
    }

    static <T extends Enum<T>> T valueOfEnum(Class<T> enumClass, String value) {
        if (StringUtils.isEmpty(value))
            throw new IllegalArgumentException("DisplayedEnum value should not be null");
        if (enumClass.isAssignableFrom(BaseEnum.class))
            throw new IllegalArgumentException("illegal DisplayedEnum type");
        T[] enums = enumClass.getEnumConstants();
        for (T t : enums) {
            BaseEnum baseEnum = (BaseEnum) t;
            if (baseEnum.getValue().equals(value))
                return (T) baseEnum;
        }
        throw new IllegalArgumentException("cannot parse integer: " + value + " to " + enumClass.getName());
    }

    default Integer getCode() {
        Field field = ReflectionUtils.findField(this.getClass(), DEFAULT_CODE_NAME);
        if (field == null) {
            return null;
        }
        try {
            field.setAccessible(true);
            return Integer.parseInt(field.get(this).toString());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    default String getValue() {
        Field field = ReflectionUtils.findField(this.getClass(), DEFAULT_VALUE_NAME);
        if (field == null)
            return null;
        try {
            field.setAccessible(true);
            return field.get(this).toString();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
