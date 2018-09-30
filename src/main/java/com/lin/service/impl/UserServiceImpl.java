package com.lin.service.impl;

import com.lin.dao.UserMapper;
import com.lin.model.User;

import com.lin.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

/**
 * @author lkmc2
 * @date 2018/9/28
 * @description 用户服务实现
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Sid sid;

    // 如果没有该事务，以非事务运行
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        User user = new User();
        user.setUsername(username);

        // 根据用户名选出用户
        User result = userMapper.selectOne(user);

        return result != null;
    }

    // 运行当前事务，如果当前没有事务，就新建一个事务
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean saveUser(User user) {
        // 创建下一个id
        String userId = sid.nextShort();
        user.setId(userId);

        // 保存用户到数据库
        int effectCount = userMapper.insert(user);

        return effectCount == 1;
    }

    @Override
    public User queryUserForLogin(String username, String password) {
        // 创建用户查询实例
        Example userExample = new Example(User.class);

        // 查询条件
        Example.Criteria criteria = userExample.createCriteria();
        // 用户名和密码需要相等
        criteria.andEqualTo("username", username);
        criteria.andEqualTo("password", password);

        // 根据查询实例进行查询用户
        return userMapper.selectOneByExample(userExample);
    }




}
