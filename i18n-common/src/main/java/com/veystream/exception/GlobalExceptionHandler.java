package com.veystream.exception;

import com.veystream.helpers.I18nHelper;
import com.veystream.http.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component("globalExceptionHandler")
public class GlobalExceptionHandler {
    @Value("${i18n.flag:false}")
    private boolean i18nFlag;

    @ExceptionHandler(value = BaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResult exceptionHandle(BaseException ex) {
        String msg = ex.getMessage();
        if (i18nFlag) {
            String i18nMsg = I18nHelper.get(String.valueOf(ex.getCode()), ex.getMessage());
            if (StringUtils.isNotBlank(i18nMsg)) {
                msg = i18nMsg;
            }
        }
        return BaseResult.builder().code(ex.getCode()).message(msg).build();
    }

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
}
