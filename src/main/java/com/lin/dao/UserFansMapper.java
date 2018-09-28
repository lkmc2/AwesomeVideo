package com.lin.dao;

import com.lin.model.UserFans;

public interface UserFansMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserFans record);

    int insertSelective(UserFans record);

    UserFans selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserFans record);

    int updateByPrimaryKey(UserFans record);
}