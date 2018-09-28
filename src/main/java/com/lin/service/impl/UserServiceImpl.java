package com.lin.service.impl;

import com.lin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lkmc2
 * @date 2018/9/28
 * @description 用户服务实现
 */
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public boolean queryUsernameIsExist(String username) {
        return false;
    }

    @Override
    public void saveUser(User user) {
        User user1 = userMapper.selectOne(user);
    }

}
