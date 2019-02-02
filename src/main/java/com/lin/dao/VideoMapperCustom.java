package com.lin.dao;

import com.lin.model.Video;
import com.lin.utils.MyMapper;
import com.lin.model.vo.VideoVo;

import java.util.List;

/**
 * 自定义视频Mapper
 */
public interface VideoMapperCustom extends MyMapper<Video> {
    /**
     * 查询所有视频
     * @return 视频视图对象列表
     */
    List<VideoVo> queryAllVideos();
}