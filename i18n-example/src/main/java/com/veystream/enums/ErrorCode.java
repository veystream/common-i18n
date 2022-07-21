package com.veystream.enums;

import lombok.Getter;

/**
 * @author Vincy.Xi
 */
@Getter
public enum ErrorCode {
    PARAM_ERROR(1000, "参数不正确");

    private int code;
    private String desc;

    ErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
