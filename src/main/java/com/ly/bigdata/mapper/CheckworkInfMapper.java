package com.ly.bigdata.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.CheckworkInf;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ly.bigdata.po.EmployeeInf;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-26
 */
@Repository
public interface CheckworkInfMapper extends BaseMapper<CheckworkInf> {
    public List<CheckworkInf> selectEmpAll(Page<CheckworkInf> page, @Param("content") String content);

}
