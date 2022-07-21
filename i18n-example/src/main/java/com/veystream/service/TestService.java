package com.veystream.service;

import com.veystream.enums.ErrorCode;
import com.veystream.exception.BaseException;
import org.springframework.stereotype.Service;

/**
 * @author Vincy.Xi
 */
@Service
public class TestService {
    public void exception() {
        throw new BaseException(ErrorCode.PARAM_ERROR.getCode(), ErrorCode.PARAM_ERROR.getDesc());
    }
}
