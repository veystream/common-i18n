package com.veystream.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface I18nField {
    /**
     * 需要国际化的参数属性名
     */
    String fieldName() default "";
    /**
     * 源参数名，当fileName和数据映射不一样时，需填写
     */
    String sourceName() default "";
    /**
     * 多语言字段是否取本类字段
     */
    boolean isSelf() default false;
}
