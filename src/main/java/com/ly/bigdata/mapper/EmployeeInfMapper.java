package com.ly.bigdata.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.EmployeeInf;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ly.bigdata.vo.DeptNum;
import com.ly.bigdata.vo.sexNum;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-25
 */
//@Repository
@Component
public interface EmployeeInfMapper extends BaseMapper<EmployeeInf> {
    List<EmployeeInf> selectEmpAll(Page<EmployeeInf> page, @Param("content") String content);
    List<sexNum> selectSexes();
}
