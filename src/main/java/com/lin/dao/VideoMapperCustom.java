package com.lin.dao;

import com.lin.model.Video;
import com.lin.utils.MyMapper;
import com.lin.model.vo.VideoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自定义视频Mapper
 */
public interface VideoMapperCustom extends MyMapper<Video> {
    /**
     * 查询用户的所有视频
     * @param videoDesc 视频描述
     * @param userId 用户id
     * @return 视频视图对象列表
     */
    List<VideoVo> queryAllVideos(@Param("videoDesc") String videoDesc, @Param("userId") String userId);

    /**
     * 添加视频喜欢（点赞）数
     * @param videoId 视频id
     */
    void addVideoLikeCount(String videoId);

    /**
     * 减少视频喜欢（点赞）数
     * @param videoId 视频id
     */
    void reduceVideoLikeCount(String videoId);

    /**
     * 获取用户点赞的视频列表
     * @param userId 用户id
     * @return 用户点赞的视频列表
     */
    List<VideoVo> queryMyLikeVideos(String userId);

    /**
     * 获取关注的人发的视频列表
     * @param userId 用户id
     * @return 关注的人发的视频列表
     */
    List<VideoVo> queryMyFollowVideos(String userId);
}