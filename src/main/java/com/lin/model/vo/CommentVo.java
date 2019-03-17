package com.lin.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lkmc2
 * @date 2019/2/6
 * @description 评论视图对象
 */
@Data
public class CommentVo implements Serializable {
    private String id;
    private String videoId; // 视频id
    private String fromUserId; // 留言者，评论的用户id
    private Date createTime; // 创建时间
    private String comment; // 评论内容

    private String faceImage; // 头像
    private String nickname; // 昵称
    private String toNickname; // 对话人的昵称
    private String timeAgoStr; // 多少分钟前
}
