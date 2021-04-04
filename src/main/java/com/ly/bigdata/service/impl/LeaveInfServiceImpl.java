package com.ly.bigdata.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ly.bigdata.mapper.LeaveInfMapper;
import com.ly.bigdata.po.LeaveInf;
import com.ly.bigdata.service.LeaveInfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-03-26
 */
@Service
public class LeaveInfServiceImpl extends ServiceImpl<LeaveInfMapper, LeaveInf> implements LeaveInfService {

    @Autowired
    private LeaveInfMapper leaveInfMapper;
    @Override
    public List<LeaveInf> findLeaveInfAll(Page<LeaveInf> page, String content) {
        if(content==null){
            content="";
        }
        List<LeaveInf> list=leaveInfMapper.findLeaveInfAll(page,"%"+content+"%");
        page.setRecords(list);
        return list;
    }
}
