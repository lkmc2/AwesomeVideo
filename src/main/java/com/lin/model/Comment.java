package com.lin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import javax.persistence.*;

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

    /**
     * 视频id
     */
    @ApiModelProperty(value = "视频Id", name = "videoId", example = "1902057AAGAS0B2W", required = true)
    @Column(name = "video_id")
    private String videoId;

    /**
     * 留言者，评论的用户id
     */
    @ApiModelProperty(value = "留言者Id", name = "fromUserId", example = "180930DRXM99CKKP", required = true)
    @Column(name = "from_user_id")
    private String fromUserId;

    @ApiModelProperty(hidden = true)
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 评论内容
     */
    @ApiModelProperty(value = "评论内容", name = "comment", example = "这个视频太好看了", required = true)
    private String comment;

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
     * @return father_comment_id
     */
    public String getFatherCommentId() {
        return fatherCommentId;
    }

    /**
     * @param fatherCommentId
     */
    public void setFatherCommentId(String fatherCommentId) {
        this.fatherCommentId = fatherCommentId;
    }

    /**
     * @return to_user_id
     */
    public String getToUserId() {
        return toUserId;
    }

    /**
     * @param toUserId
     */
    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    /**
     * 获取视频id
     *
     * @return video_id - 视频id
     */
    public String getVideoId() {
        return videoId;
    }

    /**
     * 设置视频id
     *
     * @param videoId 视频id
     */
    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    /**
     * 获取留言者，评论的用户id
     *
     * @return from_user_id - 留言者，评论的用户id
     */
    public String getFromUserId() {
        return fromUserId;
    }

    /**
     * 设置留言者，评论的用户id
     *
     * @param fromUserId 留言者，评论的用户id
     */
    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取评论内容
     *
     * @return comment - 评论内容
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置评论内容
     *
     * @param comment 评论内容
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}