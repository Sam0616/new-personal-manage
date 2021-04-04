package com.ly.bigdata.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.EmployeeInf;
import com.ly.bigdata.mapper.EmployeeInfMapper;
import com.ly.bigdata.service.EmployeeInfService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ly.bigdata.vo.sexNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-25
 */
@Service
public class EmployeeInfServiceImpl extends ServiceImpl<EmployeeInfMapper, EmployeeInf> implements EmployeeInfService {

    @Autowired
    private EmployeeInfMapper employeeInfMapper;

    @Override
    public List<EmployeeInf> selectEmpAll(Page<EmployeeInf> page, String content) {

        if (content != null && !"".equals(content)) {
            List<EmployeeInf> employeeInfs = employeeInfMapper.selectEmpAll(page, "%" + content + "%");
            page.setRecords(employeeInfs);
            return employeeInfs;
        } else {
            List<EmployeeInf> employeeInfs = employeeInfMapper.selectEmpAll(page, "%");
            page.setRecords(employeeInfs);
            return employeeInfs;
        }


    }

    @Override
    public List<sexNum> selectSexes() {
        List<sexNum> sexNums = employeeInfMapper.selectSexes();
        return sexNums;
    }
}
