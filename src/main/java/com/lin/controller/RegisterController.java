package com.lin.controller;

import com.lin.model.User;
import com.lin.model.vo.UserVo;
import com.lin.service.UserService;
import com.lin.utils.JsonResult;
import com.lin.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author lkmc2
 * @date 2018/9/28
 * @description 注册控制器
 */
@Api(value = "用户注册的接口", tags = {"注册的Controller"})
@RestController
public class RegisterController extends BaseController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户注册", notes = "用户注册的接口")
    @PostMapping("/register")
    public JsonResult register(@RequestBody User user) throws Exception {

        // 1.判断用户名和密码必须不能为空
        if (StringUtils.isBlank(user.getUsername())
                || StringUtils.isBlank(user.getPassword())) {
            return JsonResult.errorMsg("用户名和密码不能为空");
        }

        // 2.判断用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(user.getUsername());

        // 3.保存用户，注册信息
        if (!isExist) {
            user.setUsername(user.getUsername());
            user.setNickname(user.getNickname());
            // 密码进行MD5加密
            user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
            user.setFansCounts(0);
            user.setReceiveLikeCounts(0);
            user.setFollowCounts(0);

            // 保存用户到数据库
            userService.saveUser(user);
        } else {
            return JsonResult.errorMsg("用户名已存在，请更换一个再尝试");
        }

        // 清空密码
        user.setPassword("");

        // 生成Token
        String uniqueToken = UUID.randomUUID().toString();

        // 将用户信息存入Redis（有效期30分钟）
        redisOperator.set(USER_REDIS_SESSION + ":" + user.getId(), uniqueToken, 1000 * 60 * 30);

        // 用户视图对象
        UserVo userVo = new UserVo();

        // 将用户中的属性拷贝到Vo对象
        BeanUtils.copyProperties(user, userVo);

        // 设置Token到Vo对象
        userVo.setUserToken(uniqueToken);

        // 返回Vo对象
        return JsonResult.ok(userVo);
    }

}
