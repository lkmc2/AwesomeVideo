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
     * 查询所有视频
     * @param videoDesc 视频描述
     * @return 视频视图对象列表
     */
    List<VideoVo> queryAllVideos(@Param("videoDesc") String videoDesc);

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
}