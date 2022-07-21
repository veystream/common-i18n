package com.veystream.config;

import com.veystream.dao.I18nMessageDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * @author Vincy.Xi
 */
@Configuration
@ConditionalOnProperty(value = "i18n.flag")
public class I18nConfig implements WebMvcConfigurer {
    /**
     * http头语言参数,默认language
     */
    @Value(value = "${i18n.header.param:language}")
    private String language;
    /**
     * 默认语言
     */
    @Value(value = "${i18n.defaultLocale:zh_CN}")
    private String defaultLocale;
    /**
     * 默认编码
     */
    @Value(value = "${i18n.defaultEncoding:UTF-8}")
    private String defaultEncoding;
    @Resource
    I18nMessageDao i18nMessageDao;

    @Bean
    public LocaleResolver localeResolver() {
        MyLocaleResolver localeResolver = new MyLocaleResolver();
        String[] s = defaultLocale.split("_");
        localeResolver.setDefaultLocale(new Locale(s[0], s[1]));
        localeResolver.setLanguage(language);
        return localeResolver;
    }

    @Bean(name = "messageSource")
    public DataBaseBundleMessageSource getMessageResource() {
        DataBaseBundleMessageSource messageSource = new DataBaseBundleMessageSource();
        messageSource.setDefaultEncoding(defaultEncoding);
        messageSource.setI18nMessageDao(i18nMessageDao);
        return messageSource;
    }
}
