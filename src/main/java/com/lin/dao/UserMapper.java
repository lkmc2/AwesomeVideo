package com.lin.dao;

import com.lin.model.User;
import com.lin.utils.MyMapper;

public interface UserMapper extends MyMapper<User> {
    /**
     * 增加用户受喜欢数
     * @param userId 用户id
     */
    void addReceiveLikeCount(String userId);

    /**
     * 减少用户受喜欢数
     * @param userId 用户id
     */
    void reduceReceiveLikeCount(String userId);
}