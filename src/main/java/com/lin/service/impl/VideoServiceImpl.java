package com.lin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lin.dao.*;
import com.lin.model.Comment;
import com.lin.model.SearchRecords;
import com.lin.model.UserLikeVideos;
import com.lin.model.Video;
import com.lin.model.vo.CommentVo;
import com.lin.model.vo.VideoVo;
import com.lin.service.VideoService;
import com.lin.utils.PagedResult;
import com.lin.utils.TimeAgoUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author lkmc2
 * @date 2019/2/2
 * @description 视频服务实现
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;
    
    @Autowired
    private VideoMapperCustom videoMapperCustom;

    @Autowired
    private SearchRecordsMapper searchRecordsMapper;

    @Autowired
    private UserLikeVideosMapper userLikeVideosMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentMapperCustom commentMapperCustom;

    @Autowired
    private Sid sid;

    // 运行当前事务，如果当前没有事务，就新建一个事务
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String saveVideo(Video video) {
        String id = sid.nextShort();
        video.setId(id);
        // 插入视频到数据库
        videoMapper.insertSelective(video);

        return id;
    }

    // 运行当前事务，如果当前没有事务，就新建一个事务
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public PagedResult getAllVideos(Video video, Integer isSaveRecord, Integer currentPage, Integer pageSize) {
        // 获取视频描述
        String desc = video.getVideoDesc();
        // 获取用户id
        String userId = video.getUserId();

        // 当isSaveRecord为1时，保存搜索记录（热搜词）
        if (isSaveRecord != null && isSaveRecord == 1) {
            SearchRecords record = new SearchRecords();
            String recordId = sid.nextShort();
            record.setId(recordId);
            record.setContent(desc);

            // 保存搜索记录
            searchRecordsMapper.insert(record);
        }

        // 使用分页插件进行分页
        PageHelper.startPage(currentPage, pageSize);
        // 查询所有视频
        List<VideoVo> list = videoMapperCustom.queryAllVideos(desc, userId);

        // 使用分页插件生成分页信息
        PageInfo<VideoVo> pageInfo = new PageInfo<>(list);

        // 自定义分页结果
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(currentPage); // 设置当前页数
        pagedResult.setTotal(pageInfo.getPages()); // 设置总页数
        pagedResult.setRows(list); // 设置每行显示的内容
        pagedResult.setRecords(pageInfo.getTotal()); // 设置总记录数
        return pagedResult;
    }

    // 如果没有该事务，以非事务运行
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<String> getHotWords() {
        return searchRecordsMapper.getHotWords();
    }

    // 运行当前事务，如果当前没有事务，就新建一个事务
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userLikeVideo(String userId, String videoId, String videoCreatorId) {
        // 1.保存用户和视频的喜欢点赞关联关系表
        String likeId = sid.nextShort();

        // 用户和视频点赞关系对象
        UserLikeVideos ulv = new UserLikeVideos();
        ulv.setId(likeId);
        ulv.setUserId(userId);
        ulv.setVideoId(videoId);
        // 将用户和视频点赞关系插入数据库
        userLikeVideosMapper.insert(ulv);

        // 2.视频喜欢数量增加
        videoMapperCustom.addVideoLikeCount(videoId);

        // 3.用户受喜欢数量增加
        userMapper.addReceiveLikeCount(videoCreatorId);
    }

    // 运行当前事务，如果当前没有事务，就新建一个事务
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void userUnlikeVideo(String userId, String videoId, String videoCreatorId) {
        // 1.删除用户和视频的喜欢点赞关联关系表
        Example example = new Example(UserLikeVideos.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("videoId", videoId);

        // 删除用户和视频的点赞关系
        userLikeVideosMapper.deleteByExample(example);

        // 2.视频喜欢数量减少
        videoMapperCustom.reduceVideoLikeCount(videoId);

        // 3.用户受喜欢数量减少
        userMapper.reduceReceiveLikeCount(videoCreatorId);
    }

    // 运行当前事务，如果当前没有事务，就新建一个事务
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComment(Comment comment) {
        String id = sid.nextShort();
        comment.setId(id);
        comment.setCreateTime(new Date());

        // 将评论插入数据库
        commentMapper.insert(comment);
    }

    // 如果没有该事务，以非事务运行
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedResult getAllComments(String videoId, Integer page, Integer pageSize) {
        // 分页插件进行分页
        PageHelper.startPage(page, pageSize);

        // 查询视频对应的所有评论
        List<CommentVo> commentVoList = commentMapperCustom.queryComments(videoId);

        for (CommentVo commentVo : commentVoList) {
            String timeAgo = TimeAgoUtils.format(commentVo.getCreateTime());
            // 设置时间间隔字符串
            commentVo.setTimeAgoStr(timeAgo);
        }

        // 创建插件的分页信息
        PageInfo<CommentVo> pageList = new PageInfo<>(commentVoList);

        // 创建自定义分页结果对象
        PagedResult pagedResult = new PagedResult();
        pagedResult.setTotal(pageList.getPages()); // 总页数
        pagedResult.setRows(commentVoList); // 每行显示的内容
        pagedResult.setPage(page); // 当前页数
        pagedResult.setRecords(pageList.getTotal()); // 总记录数

        return pagedResult;
    }

}
