package com.veystream.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResult<E> {
    @Builder.Default
    private int code = 0;
    @Builder.Default
    private String message = "成功";
    private E data;

    @Tolerate
    public BaseResult() {
    }
}
