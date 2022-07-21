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

```

修改i18n-example下application.properties
```
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```
设置i18n开关是否开启
```aidl
i18n.flag=true
```
启动入口：i18n-example下Application

启动后post测试集


# 实现介绍
### 常量多语言
i18n-common下提供I18nHelper.get方法可以获取常量多语言。常量可以分为普通的常量和带变量的常量两种场景。
##### 1.普通常量
```aidl
    @GetMapping("/constant")
    public BaseResult<String> getConstant() {
        return BaseResult.<String>builder().data(I18nHelper.get("订单详情")).build();
    }
```
##### 2.带变量常量
```aidl
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
```aidl
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

```aidl
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
```aidl

```

### 数据库查询字段多语言

待实现

### 前端语言包
前端语言包可以通过type去定义，获取语言包时使用type来获取同一type下所有词条，并根据语言进行分组返回
```aidl
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
