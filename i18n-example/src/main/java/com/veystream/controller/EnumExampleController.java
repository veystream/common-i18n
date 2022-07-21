package com.veystream.controller;

import com.veystream.enums.Gender;
import com.veystream.http.BaseResult;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 枚举多语言
 *
 * @author Vincy.Xi
 */
@RestController
@RequestMapping("/example")
public class EnumExampleController {
    /**
     * 枚举多语言方法一：枚举内需要多语言的字段，get方法使用I18nUtil转换
     * @return
     */
    @GetMapping("/enum1")
    public BaseResult<Enum1Resp> getEnum1() {
        return BaseResult
                .<Enum1Resp>builder()
                .data(Enum1Resp.builder().code(Gender.MALE.getCode()).desc(Gender.MALE.getDesc()).build())
                .build();
    }

    @Data
    @Builder
    static class Enum1Resp {
        private int code;
        private String desc;
    }
}
