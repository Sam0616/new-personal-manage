package com.ly.bigdata.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ly.bigdata.po.TrainInf;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-29
 */
public interface TrainInfService extends IService<TrainInf> {
    List<TrainInf> selectAll(Page<TrainInf> page, @Param("content") String content);

    List<TrainInf> selectTrainPersonal(Page<TrainInf> page, @Param("content") String content, @Param("name") String name);

}
