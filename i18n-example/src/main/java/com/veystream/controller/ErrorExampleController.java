package com.veystream.controller;

import com.veystream.http.BaseResult;
import com.veystream.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 错误信息多语言
 *
 * @author Vincy.Xi
 */
@RestController
@RequestMapping("/example")
public class ErrorExampleController {
    @Resource
    private TestService testService;

    @GetMapping("/error")
    public BaseResult<String> getError() {
        testService.exception();
        return BaseResult.<String>builder().data("获取数据").build();
    }
}
