package com.lin.controller;

import com.lin.model.User;
import com.lin.service.UserService;
import com.lin.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lkmc2
 * @date 2018/9/28
 * @description 注册控制器
 */
@Api(value = "用户注册的接口", tags = {"注册的Controller"})
@RestController
public class RegisterController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户注册", notes = "用户注册的接口")
    @PostMapping("/register")
    public JsonResult register(@RequestBody User user) {

        // 1.判断用户名和密码必须不能为空
        if (StringUtils.isBlank(user.getUsername())
                || StringUtils.isBlank(user.getPassword())) {
            return JsonResult.errorMsg("用户名和密码不能为空");
        }

        // 2.判断用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(user.getUsername());

        // 3.保存用户，注册信息
        if (isExist) {
            user.setUsername(user.getUsername());
            user.setNickname(user.getNickname());
            user.setFansCounts(0);
            user.setReceiveLikeCounts(0);
            user.setFollowCounts(0);

            // 保存用户到数据库
            userService.saveUser(user);
        } else {
            return JsonResult.errorMsg("用户名已存在，请更换一个再尝试");
        }
        return JsonResult.ok();
    }

}
