package com.lin.controller;

import com.lin.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lkmc2
 * @date 2018/9/30
 * @description 基础控制器
 */
@RestController
public class BaseController {

    // 用户Redis Session名
    protected static final String USER_REDIS_SESSION = "user-redis-session";

    // Redis操作工具
    @Autowired
    public RedisOperator redisOperator;

}
