package com.ly.bigdata.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.CheckworkInf;
import com.ly.bigdata.mapper.CheckworkInfMapper;
import com.ly.bigdata.po.EmployeeInf;
import com.ly.bigdata.service.CheckworkInfService;
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
 * @since 2021-03-26
 */
@Service
public class CheckworkInfServiceImpl extends ServiceImpl<CheckworkInfMapper, CheckworkInf> implements CheckworkInfService {
    @Autowired
    private CheckworkInfMapper checkworkInfMapper;

    @Override
    public List<CheckworkInf> selectEmpAll(Page<CheckworkInf> page, String content) {
        if (content != null && !"".equals(content)) {
            List<CheckworkInf> checkworkInfs = checkworkInfMapper.selectEmpAll(page, "%" + content + "%");
            page.setRecords(checkworkInfs);
            return checkworkInfs;
        } else {
            List<CheckworkInf> checkworkInfs = checkworkInfMapper.selectEmpAll(page, "%");
            page.setRecords(checkworkInfs);
            return checkworkInfs;
        }


    }
}
