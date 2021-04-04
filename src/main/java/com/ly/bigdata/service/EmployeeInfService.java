package com.ly.bigdata.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.EmployeeInf;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ly.bigdata.vo.sexNum;
import com.sun.media.sound.FFT;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-25
 */
public interface EmployeeInfService extends IService<EmployeeInf> {
    public List<EmployeeInf> selectEmpAll(Page<EmployeeInf> page,String content);
    List<sexNum> selectSexes();}
