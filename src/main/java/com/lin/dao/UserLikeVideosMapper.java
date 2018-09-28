package com.lin.dao;

import com.lin.model.UserLikeVideos;

public interface UserLikeVideosMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserLikeVideos record);

    int insertSelective(UserLikeVideos record);

    UserLikeVideos selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserLikeVideos record);

    int updateByPrimaryKey(UserLikeVideos record);
}