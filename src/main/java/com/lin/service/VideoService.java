package com.lin.service;

import com.lin.model.Video;

/**
 * @author lkmc2
 * @date 2019/2/2
 * @description 视频服务接口
 */
public interface VideoService {
    /**
     * 保存视频
     * @param video 视频
     * @return 视频id
     */
    String saveVideo(Video video);

    /**
     * 更新视频信息
     * @param videoId 视频id
     * @param coverPath 视频封面路径
     */
    void updateVideo(String videoId, String coverPath);
}
