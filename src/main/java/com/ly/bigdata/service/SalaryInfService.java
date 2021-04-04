package com.ly.bigdata.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.LeaveInf;
import com.ly.bigdata.po.SalaryInf;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-27
 */
public interface SalaryInfService extends IService<SalaryInf> {
    List<SalaryInf> findSalaryInfAll(Page<SalaryInf> page, @Param("content") String content);
    List<SalaryInf> findSalaryInfPersonal(Page<SalaryInf> page, @Param("content") String content,@Param("empname")String name);
}
