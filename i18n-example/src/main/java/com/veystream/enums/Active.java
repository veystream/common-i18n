package com.veystream.enums;

import lombok.Getter;

/**
 * @author Vincy.Xi
 */
@Getter
public enum Active {
    INACTIVE(0, "未激活"),
    ACTIVATED(1, "已激活"),
    ;

    private int code;
    private String desc;

    Active(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
