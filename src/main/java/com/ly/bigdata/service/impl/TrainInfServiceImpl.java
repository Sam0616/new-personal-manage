package com.ly.bigdata.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.LeaveInf;
import com.ly.bigdata.po.TrainInf;
import com.ly.bigdata.mapper.TrainInfMapper;
import com.ly.bigdata.service.TrainInfService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-29
 */
@Service
public class TrainInfServiceImpl extends ServiceImpl<TrainInfMapper, TrainInf> implements TrainInfService {

    @Autowired
    private TrainInfMapper trainInfMapper;

    @Override
    public List<TrainInf> selectAll(Page<TrainInf> page, String content) {
        if (content == null) {
            content = "";
        }
        List<TrainInf> list = trainInfMapper.selectTrainAll(page, "%" + content + "%");
        page.setRecords(list);
        return list;

    }

    @Override
    public List<TrainInf> selectTrainPersonal(Page<TrainInf> page, String content, String name) {
        if (content == null) {
            content = "";
        }
        List<TrainInf> list = trainInfMapper.selectTrainPersonal(page, "%" + content + "%", name);
        page.setRecords(list);
        return list;
    }
}
