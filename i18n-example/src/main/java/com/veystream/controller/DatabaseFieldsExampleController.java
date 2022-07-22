package com.veystream.controller;

import com.veystream.entity.Goods;
import com.veystream.http.BaseResult;
import com.veystream.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 数据库字段多语言
 *
 * @author Vincy.Xi
 */
@RestController
@RequestMapping("/example")
public class DatabaseFieldsExampleController {
    @Resource
    private TestService testService;

    @GetMapping("/databaseFields")
    public BaseResult<List<Goods>> getDatabaseFields() {
        return BaseResult.<List<Goods>>builder().data(testService.getGoods()).build();
    }
}
