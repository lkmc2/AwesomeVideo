package com.lin.service;

import com.lin.model.Comment;
import com.lin.model.Video;
import com.lin.utils.PagedResult;

import java.util.List;

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
     *
     * @param video 视频对象
     * @param isSaveRecord 是否保存记录（1：保存记录；0或其他：不保存）
     * @param currentPage 当前页数
     * @param pageSize 显示的记录数
     * @return 封装分页后的数据格式
     */
    PagedResult getAllVideos(Video video, Integer isSaveRecord, Integer currentPage, Integer pageSize);

    /**
     * 获取热搜词
     * @return 热搜词列表
     */
    List<String> getHotWords();

    /**
     * 用户给视频点赞
     * @param userId 用户id
     * @param videoId 视频id
     * @param videoCreatorId 视频发布者id
     */
    void userLikeVideo(String userId, String videoId, String videoCreatorId);

    /**
     * 用户给视频取消点赞
     * @param userId 用户id
     * @param videoId 视频id
     * @param videoCreatorId 视频发布者id
     */
    void userUnlikeVideo(String userId, String videoId, String videoCreatorId);

    /**
     * 保存评论
     * @param comment 评论
     */
    void saveComment(Comment comment);
}
