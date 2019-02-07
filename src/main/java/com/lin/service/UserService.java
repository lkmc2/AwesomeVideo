package com.lin.service;

import com.lin.model.User;
import com.lin.model.UserReport;

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

    /**
     * 修改用户信息
     * @param user 用户
     */
    void updateUserInfo(User user);

    /**
     * 查询用户信息
     * @param userId 用户id
     * @return 用户
     */
    User queryUserInfo(String userId);

    /**
     * 查询用户是否给视频点赞
     * @param userId 用户id
     * @param videoId 视频id
     * @return 用户是否给该视频点赞
     */
    boolean isUserLikeVideo(String userId, String videoId);

    /**
     * 举报用户
     * @param userReport 用户举报信息
     */
    void reportUser(UserReport userReport);

    /**
     * 查询用户是否被关注
     * @param userId 用户id
     * @param fanId 粉丝id
     * @return 用户是否被粉丝关注
     */
    boolean queryIfFollow(String userId, String fanId);

    /**
     * 增加用户和粉丝的关系
     * @param userId 用户id
     * @param fanId 粉丝id
     */
    void saveUserFanRelation(String userId, String fanId);

    /**
     * 减少用户和粉丝的关系
     * @param userId 用户id
     * @param fanId 粉丝id
     */
    void deleteUserFanRelation(String userId, String fanId);

}
