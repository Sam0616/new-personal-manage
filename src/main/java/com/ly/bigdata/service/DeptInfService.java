package com.ly.bigdata.service;

import com.ly.bigdata.po.DeptInf;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ly.bigdata.vo.DeptNum;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qzhang
 * @since 2021-03-19
 */
public interface DeptInfService extends IService<DeptInf> {

    public List<DeptNum> getDeptNumbers();
}
