package com.ly.bigdata.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.SalaryInf;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-27
 */
@Repository
public interface SalaryInfMapper extends BaseMapper<SalaryInf> {
    List<SalaryInf> findSalaryInfAll(Page<SalaryInf> page, @Param("content") String content);
    List<SalaryInf> findSalaryInfPersonal(Page<SalaryInf> page, @Param("content") String content,@Param("empname")String name);

}
