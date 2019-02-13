package com.lin.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "users_like_videos")
public class UserLikeVideos {
    @Id
    private String id;

    /**
     * 用户
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 视频
     */
    @Column(name = "video_id")
    private String videoId;
}