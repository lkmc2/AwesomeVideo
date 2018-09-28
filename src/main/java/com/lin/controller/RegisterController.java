package com.lin.controller;

import com.lin.model.User;
import com.lin.utils.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lkmc2
 * @date 2018/9/28
 * @description 注册控制器
 */
@RestController
public class RegisterController {

    @PostMapping("/register")
    public JsonResult register(@RequestBody User user) {

        // 1.判断用户名和密码必须不能为空
        if (StringUtils.isBlank(user.getUsername())
                || StringUtils.isBlank(user.getPassword())) {
            return JsonResult.errorMsg("用户名和密码不能为空");
        }

        // 2.判断用户名是否存在
        // 3.保存用户，注册信息


        return JsonResult.ok();
    }

}
