package com.lin.dao;

import com.lin.model.Comment;
import com.lin.model.vo.CommentVo;
import com.lin.utils.MyMapper;

import java.util.List;

/**
 * @author lkmc2
 * @date 2019/2/6
 * @description 自定义评论Mapper
 */
public interface CommentMapperCustom extends MyMapper<Comment> {
    /**
     * 查询视频id对应的所有评论
     * @param videoId 视频id
     * @return 视频id对应的所有评论
     */
    List<CommentVo> queryComments(String videoId);
}
