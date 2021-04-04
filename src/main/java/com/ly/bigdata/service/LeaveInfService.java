package com.ly.bigdata.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ly.bigdata.po.LeaveInf;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-03-26
 */
public interface LeaveInfService extends IService<LeaveInf> {
    public List<LeaveInf> findLeaveInfAll(Page<LeaveInf> page, @Param("content") String content);

}
