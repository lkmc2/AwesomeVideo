package com.lin.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "users_fans")
public class UserFans {
    @Id
    private String id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 粉丝id
     */
    @Column(name = "fan_id")
    private String fanId;
}