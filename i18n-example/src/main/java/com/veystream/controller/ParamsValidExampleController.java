package com.veystream.controller;

import com.veystream.http.BaseResult;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * @author Vincy.Xi
 */
@Validated
@RestController
@RequestMapping("/example")
public class ParamsValidExampleController {

    @PostMapping("/paramsValid")
    public BaseResult<String> checkParamsValid(@RequestBody @Validated ParamsReq req) {
        return BaseResult.<String>builder().data(req.name).build();
    }

    @Data
    static class ParamsReq {
        @NotBlank(message = "姓名不能为空")
        private String name;
        @NotBlank(message = "内容不能为空")
        private String content;
    }
}
