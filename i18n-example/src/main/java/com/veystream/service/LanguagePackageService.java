package com.veystream.service;

import com.veystream.dao.I18nMessage;
import com.veystream.dao.I18nMessageDao;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Vincy.Xi
 */
@Service
public class LanguagePackageService {
    @Resource
    private I18nMessageDao i18nMessageDao;

    public Map<String, List<I18nMessage>> getLanguagePackage(String type) {
        Map<String, List<I18nMessage>> map = new HashMap<>();
        List<I18nMessage> list = i18nMessageDao.lambdaQuery()
                .eq(I18nMessage::getType, type)
                .list();
        if (!CollectionUtils.isEmpty(list)) {
            map = list.stream().collect(Collectors.groupingBy(I18nMessage::getLanguage));
        }
        return map;
    }
}
