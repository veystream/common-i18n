package com.veystream.interceptor;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.veystream.annotation.I18nField;
import com.veystream.annotation.I18nResource;
import com.veystream.config.I18nConfig;
import com.veystream.helpers.I18nHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Vincy.Xi
 */
@ConditionalOnBean({I18nConfig.class})
@Component
@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})
})
public class I18nInterceptor implements Interceptor {
    /**
     * 默认语言
     */
    @Value(value = "${i18n.defaultLocale:zh_CN}")
    private String defaultLocale;

    public static Map<String, MethodAccess> methodCacheMap = new ConcurrentHashMap<>();
    public static Map<String, MethodAccess> methodIdentityMap = new ConcurrentHashMap<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object resultObject = invocation.proceed();
        if (Objects.isNull(resultObject)) {
            return null;
        }
        if (resultObject instanceof ArrayList) {
            ArrayList resultList = (ArrayList) resultObject;
            if (!CollectionUtils.isEmpty(resultList)
                    && needToI18n(resultList.get(0))
                    && !ObjectUtils.isEmpty(resultList.get(0))) {
                convertList(resultList);
            }
        } else {
            if (needToI18n(resultObject)) {
                convertObject(resultObject);
            }
        }
        return resultObject;
    }

    private boolean needToI18n(Object object) {
        Class<?> objectClass = object.getClass();
        I18nResource i18nResource = AnnotationUtils.findAnnotation(objectClass, I18nResource.class);
        return Objects.nonNull(i18nResource) && (!defaultLocale.equals(LocaleContextHolder.getLocale().toString()));
    }

    private static void convertList(ArrayList list) {
        list.stream().forEach(I18nInterceptor::convertObject);
    }

    private static void convertObject(Object object) {
        if (Objects.isNull(object)) {
            return;
        }
        Class<?> objectClass = object.getClass();
        I18nResource i18nResource = AnnotationUtils.findAnnotation(objectClass, I18nResource.class);
        if (Objects.isNull(i18nResource)) {
            return;
        }
        I18nField[] i18nFields = i18nResource.i18nFields();
        if (i18nFields.length == 0) {
            return;
        }
        for (I18nField i18nField : i18nFields) {
            StringBuilder i18nKey = new StringBuilder();
            if (StringUtils.isEmpty(i18nResource.prefix())) {
                String className = objectClass.getName();
                i18nKey.append(className);
            } else {
                i18nKey.append(i18nResource.prefix());
            }
            i18nKey.append(".").append(i18nField.fieldName());
            MethodAccess methodAccess = methodCacheMap.get(object.getClass().getName());
            if (Objects.isNull(methodAccess)) {
                methodAccess = cacheMethod(object);
            }

            String identityValue = invoke(methodAccess, object, i18nResource.identityKey()).toString();
            i18nKey.append(".").append(identityValue);
            setI18nVal(methodAccess, object, i18nField.fieldName(), i18nKey.toString());
        }
    }

    private static MethodAccess cacheMethod(Object object) {
        MethodAccess methodAccess = MethodAccess.get(object.getClass());
        methodCacheMap.put(object.getClass().getName(), methodAccess);
        return methodAccess;
    }

    private static Object invoke(MethodAccess methodAccess, Object object, String fieldName) {
        int index = methodAccess.getIndex("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
        return methodAccess.invoke(object, index);
    }

    private static void setI18nVal(MethodAccess methodAccess, Object object, String fieldName, String i18nKey) {
        int index = methodAccess.getIndex("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
        String val = I18nHelper.get(i18nKey);
        if (StringUtils.isNotBlank(val)) {
            methodAccess.invoke(object, index, val);
        }
    }
}
