package com.lin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@ApiModel(value = "用户对象", description = "这是用户对象")
@Table(name = "users")
public class User implements Serializable {
    @ApiModelProperty(hidden = true)
    @Id
    private String id;

    @ApiModelProperty(value = "用户名", name = "username", example = "jack", required = true)
    private String username;

    @ApiModelProperty(value = "密码", name = "password", example = "123456", required = true)
    private String password;

    /**
     * 我的头像，如果没有默认给一张
     */
    @ApiModelProperty(hidden = true)
    @Column(name = "face_image")
    private String faceImage;

    @ApiModelProperty(value = "昵称", name = "nickname", example = "jack123")
    private String nickname;

    /**
     * 我的粉丝数量
     */
    @ApiModelProperty(hidden = true)
    @Column(name = "fans_counts")
    private Integer fansCounts;

    /**
     * 我关注的人总数
     */
    @ApiModelProperty(hidden = true)
    @Column(name = "follow_counts")
    private Integer followCounts;

    /**
     * 我接受到的赞美/收藏 的数量
     */
    @ApiModelProperty(hidden = true)
    @Column(name = "receive_like_counts")
    private Integer receiveLikeCounts;
}