package com.megaease.template.common.enums;

public enum RecordStatus {
    /**
     * 数据状态
     */
    NORMAL("normal", 0),
    DEL("deleted", 1);

    private String value;

    private Integer code;

    RecordStatus(String value, Integer code) {
        this.value = value;
        this.code = code;
    }

    public static RecordStatus parse(Integer code) {
        for (RecordStatus e : RecordStatus.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public Integer getCode() {
        return code;
    }
}
