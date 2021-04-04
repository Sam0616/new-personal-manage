package com.ly.bigdata.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.LeaveInf;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-03-26
 */
@Repository
public interface LeaveInfMapper extends BaseMapper<LeaveInf> {
    public List<LeaveInf> findLeaveInfAll(Page<LeaveInf> page, @Param("content") String content);

}
