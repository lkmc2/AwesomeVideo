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
     * @return 是否保存用户成功
     */
    boolean saveUser(User user);

    /**
     * 根据用户名和密码查询用户
     * @param username 用户名
     * @param password 密码
     * @return 查询到的用户
     */
    User queryUserForLogin(String username, String password);

}
