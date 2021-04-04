package com.ly.bigdata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.TrainInf;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-29
 */
@Repository
public interface TrainInfMapper extends BaseMapper<TrainInf> {
     List<TrainInf> selectTrainAll(Page<TrainInf> page, @Param("content") String content);
     List<TrainInf> selectTrainPersonal(Page<TrainInf> page, @Param("content") String content,@Param("name") String name);
}
