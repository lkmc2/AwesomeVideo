package com.lin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lin.dao.VideoMapper;
import com.lin.dao.VideoMapperCustom;
import com.lin.model.Video;
import com.lin.model.vo.VideoVo;
import com.lin.service.VideoService;
import com.lin.utils.PagedResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public PagedResult getAllVideos(Integer currentPage, Integer pageSize) {
        // 使用分页插件进行分页
        PageHelper.startPage(currentPage, pageSize);
        // 查询所有视频
        List<VideoVo> list = videoMapperCustom.queryAllVideos();

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

}
