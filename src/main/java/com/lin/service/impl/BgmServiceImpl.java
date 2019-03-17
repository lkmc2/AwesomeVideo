package com.lin.service.impl;

import com.lin.dao.BgmMapper;
import com.lin.model.Bgm;
import com.lin.model.User;
import com.lin.service.BgmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author lkmc2
 * @date 2019/2/1
 * @description 背景乐服务实现
 */
@Service
@CacheConfig(cacheNames = {"BgmServiceImpl"})
public class BgmServiceImpl implements BgmService {

    @Autowired
    private BgmMapper bgmMapper;

    @Override
    @Cacheable(key = "targetClass + methodName")
    public List<Bgm> queryBgmList() {
        return bgmMapper.selectAll();
    }

    @Override
    @Cacheable(key = "targetClass + methodName + #p0")
    public Bgm queryBgmById(String bgmId) {
        // 创建背景音乐查询实例
        Example bgmExample = new Example(Bgm.class);

        // 查询条件
        Example.Criteria criteria = bgmExample.createCriteria();
        // 用户id需相等
        criteria.andEqualTo("id", bgmId);
        return bgmMapper.selectOneByExample(bgmExample);
    }

}
