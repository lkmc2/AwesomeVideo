package com.lin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@ApiModel(value = "视频对象", description = "这是视频对象")
@Table(name = "videos")
public class Video {
    @ApiModelProperty(value = "视频id", name = "id", example = "10001", required = true)
    @Id
    private String id;

    @ApiModelProperty(value = "发布者id", name = "userId", example = "180425BNSR1CG0H0", required = true)
    @Column(name = "user_id")
    private String userId;

    @ApiModelProperty(value = "用户使用音频的id", name = "audioId", example = "18052674D26HH32P")
    @Column(name = "audio_id")
    private String audioId;

    @ApiModelProperty(value = "视频描述", name = "videoDesc")
    @Column(name = "video_desc")
    private String videoDesc;

    /**
     * 视频存放的路径
     */
    @Column(name = "video_path")
    private String videoPath;

    /**
     * 视频秒数
     */
    @Column(name = "video_seconds")
    private Float videoSeconds;

    /**
     * 视频宽度
     */
    @Column(name = "video_width")
    private Integer videoWidth;

    /**
     * 视频高度
     */
    @Column(name = "video_height")
    private Integer videoHeight;

    /**
     * 视频封面图
     */
    @Column(name = "cover_path")
    private String coverPath;

    /**
     * 喜欢/赞美的数量
     */
    @Column(name = "like_counts")
    private Long likeCounts;

    /**
     * 视频状态：
     1、发布成功
     2、禁止播放，管理员操作
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}