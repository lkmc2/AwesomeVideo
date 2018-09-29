package com.lin.service;

import com.lin.model.User;

/**
 * @author lkmc2
 * @date 2018/9/28
 * @description 用户服务接口
 */
public interface UserService {

    /**
     * 判断用户名是否存在
     * @param username 用户名
     * @return 该用户在数据库中是否存在
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 保存用户到数据库
     * @param user 用户
     */
    void saveUser(User user);

}
