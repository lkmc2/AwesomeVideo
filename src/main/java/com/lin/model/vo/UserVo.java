package com.lin.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "用户视图对象", description = "这是用户视图对象")
public class UserVo implements Serializable {

    @ApiModelProperty(hidden = true)
    private String id;

    /*** 用户认证凭据 ***/
    @ApiModelProperty(hidden = true)
    private String userToken;

    /*** 用户名 ***/
    @ApiModelProperty(value = "用户名", name = "username", example = "jack", required = true)
    private String username;

    /*** 密码 ***/
    @ApiModelProperty(value = "密码", name = "password", example = "123456", required = true)
    @JsonIgnore
    private String password;

    @ApiModelProperty(value = "是否关注该用户", name = "username", example = "jack", required = true)
    private boolean isFollow;
    /*** 我的头像，如果没有默认给一张 ***/
    @ApiModelProperty(hidden = true)
    private String faceImage;

    /*** 昵称 ***/
    @ApiModelProperty(value = "昵称", name = "nickname", example = "jack123")
    private String nickname;

    /*** 我的粉丝数量 ***/
    @ApiModelProperty(hidden = true)
    private Integer fansCounts;

    /*** 我关注的人总数 ***/
    @ApiModelProperty(hidden = true)
    private Integer followCounts;

    /*** 我接受到的赞美/收藏 的数量 ***/
    @ApiModelProperty(hidden = true)
    private Integer receiveLikeCounts;
}