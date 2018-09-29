package com.lin.model;


public class UserFans {
    private String id;

    /**
     * 用户
     */
    private String userId;

    /**
     * 粉丝
     */
    private String fanId;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取用户
     *
     * @return user_id - 用户
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户
     *
     * @param userId 用户
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取粉丝
     *
     * @return fan_id - 粉丝
     */
    public String getFanId() {
        return fanId;
    }

    /**
     * 设置粉丝
     *
     * @param fanId 粉丝
     */
    public void setFanId(String fanId) {
        this.fanId = fanId;
    }
}