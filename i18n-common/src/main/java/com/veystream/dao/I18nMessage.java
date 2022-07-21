package com.veystream.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Vincy.Xi
 */
@Data
@TableName(value = "i18n_message")
public class I18nMessage {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 类型：常量；枚举；错误码；数据库表；
     */
    private String type;
    /**
     * 词条
     */
    private String code;
    /**
     * 词条内容
     */
    private String text;
    /**
     * 语言
     */
    private String language;
    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;
}
