package com.ly.bigdata.mapper;

import com.ly.bigdata.po.DeptInf;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ly.bigdata.vo.DeptNum;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-23
 */
@Repository
public interface DeptInfMapper extends BaseMapper<DeptInf> {

    public List<DeptNum> getDeptNumbers();
}
