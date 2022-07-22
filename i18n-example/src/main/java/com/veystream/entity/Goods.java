package com.veystream.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.veystream.annotation.I18nField;
import com.veystream.annotation.I18nResource;
import lombok.Data;

/**
 * @author Vincy.Xi
 */
@Data
@I18nResource(
        prefix = "Goods",
        identityKey = "id",
        i18nFields = {
            @I18nField(fieldName = "name"),
            @I18nField(fieldName = "description"),
            @I18nField(fieldName = "image"),
})
@TableName(value = "t_goods")
public class Goods {
    /**
     * 商品id
     */
    private Long id;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品描述
     */
    private String description;
    /**
     * 商品图片
     */
    private String image;
}
