package com.lin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "用户举报对象", description = "这是用户举报对象")
@Table(name = "users_report")
public class UserReport implements Serializable {
    @ApiModelProperty(hidden = true)
    @Id
    private String id;

    @ApiModelProperty(value = "被举报的用户id", name = "dealUserId", example = "180930DRXM99CKKP", required = true)
    @Column(name = "deal_user_id")
    private String dealUserId;

    @ApiModelProperty(value = "被举报的视频id", name = "dealVideoId", example = "190204HCS3P9539P", required = true)
    @Column(name = "deal_video_id")
    private String dealVideoId;

    /**
     * 类型标题，让用户选择，详情见 枚举
     */
    @ApiModelProperty(value = "举报标题", name = "title", example = "广告垃圾", required = true)
    private String title;

    @ApiModelProperty(value = "举报详情说明", name = "title", example = "内容影响身心健康", required = true)
    private String content;

    @ApiModelProperty(value = "举报人的id", name = "userId", example = "180425B0B3N6B25P", required = true)
    @Column(name = "userid")
    private String userId;

    /**
     * 举报时间
     */
    @ApiModelProperty(hidden = true)
    @Column(name = "create_date")
    private Date createDate;
}