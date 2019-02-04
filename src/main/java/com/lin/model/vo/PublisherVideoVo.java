package com.lin.model.vo;

import io.swagger.annotations.ApiModel;

/**
 * @author lkmc2
 * @date 2019/2/4
 * @description 发布者与视频视图对象
 */
@ApiModel(value = "发布者与视频视图对象", description = "这是发布者与视频视图对象")
public class PublisherVideoVo {
    private UserVo publisher; // 视频发布者
    private boolean userLikeVideo; // 当前用户视频给该视频点赞

    public UserVo getPublisher() {
        return publisher;
    }

    public void setPublisher(UserVo publisher) {
        this.publisher = publisher;
    }

    public boolean isUserLikeVideo() {
        return userLikeVideo;
    }

    public void setUserLikeVideo(boolean userLikeVideo) {
        this.userLikeVideo = userLikeVideo;
    }
}
