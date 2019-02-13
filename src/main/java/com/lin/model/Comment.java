package com.lin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@ApiModel(value = "评论对象", description = "这是评论对象")
@Table(name = "comments")
public class Comment {
    @ApiModelProperty(value = "评论Id", name = "videoId", example = "1902057AAGAS0000", required = true)
    @Id
    private String id;

    @Column(name = "father_comment_id")
    @ApiModelProperty(value = "父评论Id", name = "fatherCommentId", example = "1805240G4G19R0PH", required = true)
    private String fatherCommentId;

    @ApiModelProperty(value = "被评论者Id", name = "toUserId", example = "180930HXSB796AK4", required = true)
    @Column(name = "to_user_id")
    private String toUserId;

    @ApiModelProperty(value = "视频Id", name = "videoId", example = "1902057AAGAS0B2W", required = true)
    @Column(name = "video_id")
    private String videoId;

    @ApiModelProperty(value = "留言者Id", name = "fromUserId", example = "180930DRXM99CKKP", required = true)
    @Column(name = "from_user_id")
    private String fromUserId;

    @ApiModelProperty(hidden = true)
    @Column(name = "create_time")
    private Date createTime;

    @ApiModelProperty(value = "评论内容", name = "comment", example = "这个视频太好看了", required = true)
    private String comment;
}