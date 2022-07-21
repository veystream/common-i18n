package com.veystream.controller;

import com.veystream.helpers.I18nHelper;
import com.veystream.http.BaseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量多语言
 *
 * @author Vincy.Xi
 */
@RestController
@RequestMapping("/example")
public class ConstantExampleController {

    /**
     * 普通常量多语言转换
     *
     * @return
     */
    @GetMapping("/constant1")
    public BaseResult<String> getConstant1() {
        return BaseResult.<String>builder().data(I18nHelper.get("订单详情")).build();
    }


    /**
     * 带变量的常量多语言转换
     *
     * @return
     */
    @GetMapping("/constant2")
    public BaseResult<String> getConstant2() {
        String text = "${name} ${gender},你好";
        Map<String, String> params = new HashMap<>();
        params.put("name", "张三");
        params.put("gender", I18nHelper.get(Gender.M.desc));
        return BaseResult.<String>builder().data(I18nHelper.get(params, text)).build();
    }

    static enum Gender {
        M("先生"),
        F("女士");

        private String desc;

        Gender(String desc) {
            this.desc = desc;
        }
    }
}
