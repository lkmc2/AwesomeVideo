package com.lin.online_form.model;

public class UsersLikeVideos {
    private String id;

    private String userId;

    private String videoId;

    public UsersLikeVideos(String id, String userId, String videoId) {
        this.id = id;
        this.userId = userId;
        this.videoId = videoId;
    }

    public UsersLikeVideos() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId == null ? null : videoId.trim();
    }
}