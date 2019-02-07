package com.lin.service.impl;

import com.lin.dao.UserFansMapper;
import com.lin.dao.UserLikeVideosMapper;
import com.lin.dao.UserMapper;
import com.lin.dao.UserReportMapper;
import com.lin.model.User;

import com.lin.model.UserFans;
import com.lin.model.UserLikeVideos;
import com.lin.model.UserReport;
import com.lin.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

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
    private UserLikeVideosMapper userLikeVideosMapper;

    @Autowired
    private UserReportMapper userReportMapper;

    @Autowired
    private UserFansMapper userFansMapper;

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

    // 如果没有该事务，以非事务运行
    @Transactional(propagation = Propagation.SUPPORTS)
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

    // 运行当前事务，如果当前没有事务，就新建一个事务
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserInfo(User user) {
        // 创建用户查询实例
        Example userExample = new Example(User.class);

        // 查询条件
        Example.Criteria criteria = userExample.createCriteria();
        // 用户id需相等
        criteria.andEqualTo("id", user.getId());

        // 根据查询条件选择性更新用户信息
        userMapper.updateByExampleSelective(user, userExample);
    }

    // 如果没有该事务，以非事务运行
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public User queryUserInfo(String userId) {
        // 创建用户查询实例
        Example userExample = new Example(User.class);

        // 查询条件
        Example.Criteria criteria = userExample.createCriteria();
        // 用户id需相等
        criteria.andEqualTo("id", userId);

        // 根据查询实例进行查询用户
        return userMapper.selectOneByExample(userExample);
    }

    // 如果没有该事务，以非事务运行
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean isUserLikeVideo(String userId, String videoId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(videoId)) {
            return false;
        }

        // 新建查询对象
        Example example = new Example(UserLikeVideos.class);
        Example.Criteria criteria = example.createCriteria();

        // 用户id和视频id都需相等
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("videoId", videoId);

        List<UserLikeVideos> list = userLikeVideosMapper.selectByExample(example);

        return list != null && list.size() > 0;
    }

    @Override
    public void reportUser(UserReport userReport) {
        String reportId = sid.nextShort();
        userReport.setId(reportId);
        userReport.setCreateDate(new Date());

        // 插入用户举报记录
        userReportMapper.insert(userReport);
    }

    @Override
    public boolean queryIfFollow(String userId, String fanId) {
        // 创建查询对象
        Example example = new Example(UserFans.class);
        Example.Criteria criteria = example.createCriteria();

        // 查询条件：用户id和粉丝id都相等
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("fanId", fanId);

        // 根据查询条件获取用户粉丝关联关系列表
        List<UserFans> list = userFansMapper.selectByExample(example);

        // 列表非空则已关注
        return !CollectionUtils.isEmpty(list);
    }

}
