package com.veystream.config;

import com.veystream.dao.I18nMessage;
import com.veystream.dao.I18nMessageDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.AbstractResourceBasedMessageSource;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Vincy.Xi
 */
@Slf4j
public class DataBaseBundleMessageSource extends AbstractResourceBasedMessageSource {

    private I18nMessageDao i18nMessageDao;

    private final ConcurrentMap<Locale, ConcurrentHashMap<Object, String>> cachedMergedProperties = new ConcurrentHashMap<>();

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        if (ObjectUtils.isEmpty(code)) {
            return null;
        }
        ConcurrentHashMap<Object, String> map = cachedMergedProperties.get(locale);
        if (map == null) {
            map = new ConcurrentHashMap<>();
            cachedMergedProperties.putIfAbsent(locale, map);
        }
        String msg = map.get(code);
        if (!ObjectUtils.isEmpty(msg)) {
            return createMessageFormat(msg, locale);
        }
        //查询数据库
        List<I18nMessage> list = i18nMessageDao.lambdaQuery()
                .eq(I18nMessage::getCode, code)
                .eq(I18nMessage::getLanguage, locale.toString())
                .list();
        if (!CollectionUtils.isEmpty(list)) {
            msg = list.get(0).getText();
            MessageFormat messageFormat = createMessageFormat(msg, locale);
            map.putIfAbsent(code, msg);
            return messageFormat;
        }
        log.warn("[{}]查询不到[{}]语言配置", code, locale);
        return null;
    }

    public I18nMessageDao getI18nMessageDao() {
        return i18nMessageDao;
    }

    public void setI18nMessageDao(I18nMessageDao i18nMessageDao) {
        this.i18nMessageDao = i18nMessageDao;
    }
}
