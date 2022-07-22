package com.veystream.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface I18nResource {
    /**
     * 国际化资源前缀，默认空则获取注解类全路径+类名
     */
    String prefix() default "";

    /**
     * 后缀唯一对象key
     */
    String identityKey();

    /**
     * 当前类中需要国际化的字段合集
     */
    I18nField[] i18nFields() default {};
}
