package com.lin.service.impl;

import com.lin.dao.BgmMapper;
import com.lin.model.Bgm;
import com.lin.service.BgmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lkmc2
 * @date 2019/2/1
 * @description 背景乐服务实现
 */
@Service
public class BgmServiceImpl implements BgmService {

    @Autowired
    private BgmMapper bgmMapper;

    @Override
    public List<Bgm> queryBgmList() {
        return bgmMapper.selectAll();
    }

}
