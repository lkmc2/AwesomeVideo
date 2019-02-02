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

    // Redis操作工具
    @Autowired
    protected RedisOperator redis;

    // 用户Redis Session名
    protected static final String USER_REDIS_SESSION = "user-redis-session";

    // 静态资源所在路径
    protected static final String FILE_BASE = "F:/AwesomeVideoUpload";

    // ffmpeg所在路径
    protected static final String FFMPEG_EXE = "H:/ffmpeg/bin/ffmpeg.exe";

    // 每页分页的记录数
    protected static final Integer PAGE_SIZE = 5;

}
