package com.lin.service.impl;

import com.lin.dao.VideoMapper;
import com.lin.model.Video;
import com.lin.service.VideoService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lkmc2
 * @date 2019/2/2
 * @description 用户服务实现
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;

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
    public void updateVideo(String videoId, String coverPath) {
        Video video = new Video();
        video.setId(videoId);
        // 设置视频封面路径
        video.setCoverPath(coverPath);

        // 更新视频信息
        videoMapper.updateByPrimaryKeySelective(video);
    }

}
