package com.lin.model;

public class UsersFans {
    private String id;

    private String userId;

    private String fanId;

    public UsersFans(String id, String userId, String fanId) {
        this.id = id;
        this.userId = userId;
        this.fanId = fanId;
    }

    public UsersFans() {
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

    public String getFanId() {
        return fanId;
    }

    public void setFanId(String fanId) {
        this.fanId = fanId == null ? null : fanId.trim();
    }
}