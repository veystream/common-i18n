# 项目结构
### i18n-common
通用多语言包

### i18n-example
多语言例子


# 项目启动

新建数据结构
```
CREATE TABLE `i18n_message` (
`id` int NOT NULL AUTO_INCREMENT,
`type` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '词条类型',
`code` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '词条',
`text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin COMMENT '词条对应的多语言内容',
`language` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '语言',
`created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
`updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin`
```

i18n-example中案例初始化数据（可选）
```
INSERT INTO `i18n`.`i18n_message`(`id`, `type`, `code`, `text`, `language`, `created_time`, `updated_time`) VALUES (1, 'Constant', '男', 'Male', 'zh_CN', now(), now());
INSERT INTO `i18n`.`i18n_message`(`id`, `type`, `code`, `text`, `language`, `created_time`, `updated_time`) VALUES (2, 'ErrorCode', '1000', 'Params error', 'en_US', now(), now());
INSERT INTO `i18n`.`i18n_message`(`id`, `type`, `code`, `text`, `language`, `created_time`, `updated_time`) VALUES (3, 'APP1-LanguagePackage', '确定', 'confirm', 'en_US', now(), now());
INSERT INTO `i18n`.`i18n_message`(`id`, `type`, `code`, `text`, `language`, `created_time`, `updated_time`) VALUES (4, 'APP1-LanguagePackage', '取消', 'cancel', 'en_US', now(), now());
INSERT INTO `i18n`.`i18n_message`(`id`, `type`, `code`, `text`, `language`, `created_time`, `updated_time`) VALUES (5, 'APP1-LanguagePackage', '确定', '确定', 'zh_CN', now(), now());
INSERT INTO `i18n`.`i18n_message`(`id`, `type`, `code`, `text`, `language`, `created_time`, `updated_time`) VALUES (6, 'APP1-LanguagePackage', '取消', '取消', 'zh_CN', now(), now());
INSERT INTO `i18n`.`i18n_message`(`id`, `type`, `code`, `text`, `language`, `created_time`, `updated_time`) VALUES (7, 'Constant', '订单详情', 'Order Detail', 'en_US', now(), now());
INSERT INTO `i18n`.`i18n_message`(`id`, `type`, `code`, `text`, `language`, `created_time`, `updated_time`) VALUES (8, 'Constant', '{name}{gender},你好', 'Hello,{gender} {name}', 'en_US', now(), now());
INSERT INTO `i18n`.`i18n_message`(`id`, `type`, `code`, `text`, `language`, `created_time`, `updated_time`) VALUES (9, 'Constant', '先生', 'Mr', 'en_US', now(), now());
INSERT INTO `i18n`.`i18n_message`(`id`, `type`, `code`, `text`, `language`, `created_time`, `updated_time`) VALUES (10, 'ParamsValid', '姓名不能为空', 'Name required', 'en_US', now(), now());
INSERT INTO `i18n`.`i18n_message`(`id`, `type`, `code`, `text`, `language`, `created_time`, `updated_time`) VALUES (11, 'ParamsValid', '内容不能为空', 'Content required', 'en_US', now(), now());
INSERT INTO `i18n`.`i18n_message`(`id`, `type`, `code`, `text`, `language`, `created_time`, `updated_time`) VALUES (13, 'db', 'Goods.description.1', 'goods_description!', 'en_US', now(), now());
INSERT INTO `i18n`.`i18n_message`(`id`, `type`, `code`, `text`, `language`, `created_time`, `updated_time`) VALUES (14, 'db', 'Goods.image.1', 'goods_image!', 'en_US', now(), now());
```

修改i18n-example下application.properties
```
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```
设置i18n开关是否开启
```
i18n.flag=true
```
启动入口：i18n-example下Application

启动后postman测试集在postman文件下，可以导入postman后测试接口场景


# 实现介绍
### 常量多语言
i18n-common下提供I18nHelper.get方法可以获取常量多语言。常量可以分为普通的常量和带变量的常量两种场景。
##### 1.普通常量
```
    @GetMapping("/constant")
    public BaseResult<String> getConstant() {
        return BaseResult.<String>builder().data(I18nHelper.get("订单详情")).build();
    }
```
##### 2.带变量常量
```
    @GetMapping("/constant2")
    public BaseResult<String> getConstant2() {
        String text = "${name} ${gender},你好";
        Map<String, String> params = new HashMap<>();
        params.put("name", "张三");
        params.put("gender", I18nHelper.get(Gender.M.desc));
        return BaseResult.<String>builder().data(I18nHelper.get(params, text)).build();
    }
```

### 枚举多语言
枚举中涉及多语言的中英文字段可以改写get方法。
```
@Getter
public enum Gender {
    MALE(1, "男"),
    FEMALE(2, "女"),
    UNKNOW(3, "未知")
    ;

    private int code;
    private String desc;

    Gender(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getDesc() {
        return I18nHelper.get(desc, desc);
    }
}
```

### 错误信息多语言
i18n-common下定义通用异常类BaseException，并定义GlobalExceptionHandler用于捕获异常，捕获到异常后对输出的message进行多语言转换处理

```
@ExceptionHandler(value = BaseException.class)
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public BaseResult exceptionHandle(BaseException ex) {
    String msg = ex.getMessage();
    if (i18nFlag) {
        String i18nMsg = I18nHelper.get(String.valueOf(ex.getCode()));
        if (StringUtils.isNotBlank(i18nMsg)) {
            msg = i18nMsg;
        }
    }
    return BaseResult.builder().code(ex.getCode()).message(msg).build();
}
```

### 参数校验多语言
i18n-common下定义GlobalExceptionHandler捕获HttpMessageNotReadableException异常，对返回message做多语言转换
```
@ExceptionHandler(value = MethodArgumentNotValidException.class)
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public BaseResult exceptionHandle(MethodArgumentNotValidException ex) {
    List<String> errors = new ArrayList<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
        if (error instanceof FieldError) {
            errors.add(I18nHelper.get(error.getDefaultMessage()));
        } else if (error instanceof ObjectError) {
            errors.add(I18nHelper.get(error.getDefaultMessage()));
        }
    });
    log.error(ex.getMessage(), ex);
    return BaseResult.builder().code(-1).message(StringUtils.joinWith(";", errors.toArray())).build();
}
```

### 数据库查询字段多语言
实体上使用注解I18nResource。identityKey为实体的主键id，prefix为词条前缀，不填写则为类全名。i18nFields为需要多语言的字段。词条的完整拼接规则为prefix.fieldName.identityKey
```
@Data
@I18nResource(
        prefix = "Goods",
        identityKey = "id",
        i18nFields = {
                @I18nField(fieldName = "name"),
                @I18nField(fieldName = "description"),
                @I18nField(fieldName = "image"),
        }
)
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
```

### 前端语言包
前端语言包可以通过type去定义，获取语言包时使用type来获取同一type下所有词条，并根据语言进行分组返回
```
@RestController
@RequestMapping("/languagePackage")
public class LanguagePackageController {
    @Resource
    private LanguagePackageService languagePackageService;
    
    @GetMapping
    public BaseResult<Map<String, List<I18nMessage>>> getLanguagePackage(@RequestParam(name = "type") String type) {
        return BaseResult.<Map<String, List<I18nMessage>>>builder().data(languagePackageService.getLanguagePackage(type)).build();
    }
}
```

# 后期计划
##### 支持本地properties、Nacos、数据库配置多语言词条
##### 缓存机制优化
