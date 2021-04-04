package com.ly.bigdata.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.ContractInf;
import com.ly.bigdata.mapper.ContractInfMapper;
import com.ly.bigdata.service.ContractInfService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-28
 */
@Service
public class ContractInfServiceImpl extends ServiceImpl<ContractInfMapper, ContractInf> implements ContractInfService {

    @Autowired
    private ContractInfMapper contractInfMapper;

    @Override
    public List<ContractInf> findContractInfAll(Page<ContractInf> page, String content) {
        if (content == null) {
            content = "";
        }
        List<ContractInf> list = contractInfMapper.findSalaryInfAll(page, "%" + content + "%");
        page.setRecords(list);
        return list;
    }

    @Override
    public List<ContractInf> findSalaryPersonal(Page<ContractInf> page, String content, String name) {
        if (content == null) {
            content = "";
        }
        List<ContractInf> list = contractInfMapper.findSalaryPersonal(page, "%" + content + "%",name);
        page.setRecords(list);
        return list;
    }
}
