package com.ly.bigdata.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.ContractInf;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ly.bigdata.po.SalaryInf;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-28
 */
@Repository
public interface ContractInfMapper extends BaseMapper<ContractInf> {
    List<ContractInf> findSalaryInfAll(Page<ContractInf> page, @Param("content") String content);
    List<ContractInf> findSalaryPersonal(Page<ContractInf> page, @Param("content") String content,@Param("name")String name);

}
