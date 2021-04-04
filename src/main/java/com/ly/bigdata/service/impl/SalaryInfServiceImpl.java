package com.ly.bigdata.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.mapper.LeaveInfMapper;
import com.ly.bigdata.po.LeaveInf;
import com.ly.bigdata.po.SalaryInf;
import com.ly.bigdata.mapper.SalaryInfMapper;
import com.ly.bigdata.service.SalaryInfService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-27
 */
@Service
public class SalaryInfServiceImpl extends ServiceImpl<SalaryInfMapper, SalaryInf> implements SalaryInfService {
    @Autowired
    private SalaryInfMapper salaryInfMapper;
    @Override
    public List<SalaryInf> findSalaryInfAll(Page<SalaryInf> page, String content) {
        if(content==null){
            content="";
        }
        List<SalaryInf> list=salaryInfMapper.findSalaryInfAll(page,"%"+content+"%");
        page.setRecords(list);
        return list;
    }

    @Override
    public List<SalaryInf> findSalaryInfPersonal(Page<SalaryInf> page, String content, String name) {
        if(content==null){
            content="";
        }
        List<SalaryInf> list = salaryInfMapper.findSalaryInfPersonal(page, "%" + content + "%", name);
        page.setRecords(list);
        return list;
    }
}
