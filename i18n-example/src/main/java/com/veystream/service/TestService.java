package com.veystream.service;

import com.veystream.dao.GoodsDao;
import com.veystream.entity.Goods;
import com.veystream.enums.ErrorCode;
import com.veystream.exception.BaseException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Vincy.Xi
 */
@Service
public class TestService {
    @Resource
    private GoodsDao goodsDao;

    public void exception() {
        throw new BaseException(ErrorCode.PARAM_ERROR.getCode(), ErrorCode.PARAM_ERROR.getDesc());
    }

    public List<Goods> getGoods() {
        return goodsDao.lambdaQuery().list();
    }
}
