package com.veystream.helpers;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @author Vincy.Xi
 */
@Component
public class I18nHelper {
    private static MessageSource messageSource;

    public I18nHelper(MessageSource messageSource) {
        I18nHelper.messageSource = messageSource;
    }

    public static String get(MessageSourceResolvable resolvable) {
        return messageSource.getMessage(resolvable, LocaleContextHolder.getLocale());
    }

    public static String get(String msgKey) {
        return get(msgKey, msgKey, new Object[]{});
    }

    public static String get(String msgKey, String defaultMsg) {
        String ret = get(msgKey, defaultMsg, new Object[]{});
        if (StringUtils.isBlank(ret)) {
            return defaultMsg;
        }
        return ret;
    }

    public static String get(Map<String, String> params, String msgKey) {
        String ret = get(msgKey);
        if (params == null) {
            return ret;
        }
        StringSubstitutor sub = new StringSubstitutor(params);
        return get(sub.replace(ret));
    }

    private static String get(String msgKey, String defaultKey, Object... args) {
        String ret = messageSource.getMessage(msgKey, args, "", LocaleContextHolder.getLocale());
        if (StringUtils.isBlank(ret)) {
            return defaultKey;
        }
        return ret;
    }
}
