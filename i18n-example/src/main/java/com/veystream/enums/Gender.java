package com.veystream.enums;

import com.veystream.helpers.I18nHelper;
import lombok.Getter;

/**
 * @author Vincy.Xi
 */
@Getter
public enum Gender {
    MALE(1, "男"),
    FEMALE(2, "女"),
    UNKNOW(3, "未知")
    ;

    private int code;
    private String desc;

    Gender(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getDesc() {
        return I18nHelper.get(desc, desc);
    }
}
