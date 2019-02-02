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
     */
    void saveVideo(Video video);
}
