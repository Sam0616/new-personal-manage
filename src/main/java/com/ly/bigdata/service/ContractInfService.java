package com.ly.bigdata.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ly.bigdata.po.ContractInf;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-28
 */
public interface ContractInfService extends IService<ContractInf> {
    List<ContractInf> findContractInfAll(Page<ContractInf> page, @Param("content") String content);
    List<ContractInf> findSalaryPersonal(Page<ContractInf> page, @Param("content") String content,@Param("name")String name);

}
