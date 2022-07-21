package com.veystream.config;

import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.i18n.AbstractLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author Vincy.Xi
 */
public class MyLocaleResolver extends AbstractLocaleResolver {
    private String language;

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        Locale locale = null;
        String headerLanguage = request.getHeader(language);
        if (ObjectUtils.isEmpty(headerLanguage)) {
            locale = getDefaultLocale();
        } else {
            String[] s = headerLanguage.split("_");
            locale = new Locale(s[0], s[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
    }

    @Nullable
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
