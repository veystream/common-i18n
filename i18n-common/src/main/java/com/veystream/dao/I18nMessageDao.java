package com.veystream.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.veystream.config.I18nConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

/**
 * @author Vincy.Xi
 */
@Component
@ConditionalOnBean(value = I18nConfig.class)
public class I18nMessageDao extends ServiceImpl<I18nMessageMapper, I18nMessage> {
}
