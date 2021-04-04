package com.ly.bigdata.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.CheckworkInf;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-26
 */
public interface CheckworkInfService extends IService<CheckworkInf> {
     List<CheckworkInf> selectEmpAll(Page<CheckworkInf> page, @Param("content") String content);
}
