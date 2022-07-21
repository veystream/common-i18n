package com.veystream.controller;

import com.veystream.dao.I18nMessage;
import com.veystream.http.BaseResult;
import com.veystream.service.LanguagePackageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 前端语言包
 *
 * @author Vincy.Xi
 */
@RestController
@RequestMapping("/languagePackage")
public class LanguagePackageController {
    @Resource
    private LanguagePackageService languagePackageService;

    @GetMapping
    public BaseResult<Map<String, List<I18nMessage>>> getLanguagePackage(@RequestParam(name = "type") String type) {
        return BaseResult.<Map<String, List<I18nMessage>>>builder().data(languagePackageService.getLanguagePackage(type)).build();
    }
}
