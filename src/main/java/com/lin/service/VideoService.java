package com.lin.service;

import com.lin.model.Video;
import com.lin.utils.PagedResult;

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
     * 分页查询视频列表
     * @param currentPage 当前页数
     * @param pageSize 显示的记录数
     * @return 封装分页后的数据格式
     */
    PagedResult getAllVideos(Integer currentPage, Integer pageSize);
}
