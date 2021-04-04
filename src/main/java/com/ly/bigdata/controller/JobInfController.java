package com.ly.bigdata.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.DeptInf;
import com.ly.bigdata.po.JobInf;
import com.ly.bigdata.service.JobInfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-25
 */
@Controller
@RequestMapping("/job")
public class JobInfController {
    @Autowired
    private JobInfService jobInfService;

    @RequestMapping("/list")
    public String list(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                       @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                       Model model,
                       @RequestParam(value = "content",required = false) String content){
        Page<JobInf> page=new Page<>(pageNo,pageSize);
        if (content!=null&&!content.equals("")){
            QueryWrapper<JobInf> wrapper=new QueryWrapper<>();
            wrapper.like("name",content)
                    .or().like("remark",content);
            jobInfService.page(page,wrapper);
        }else {
            jobInfService.page(page,null);
        }
        model.addAttribute("list",page.getRecords());
        model.addAttribute("pageNo",page.getCurrent());//当前页
        model.addAttribute("pageSize",page.getSize());////每页记录数
        model.addAttribute("count",page.getTotal());//总记录数
         /*System.out.println("当前页："+page.getCurrent());
        System.out.println("每页记录数："+page.getSize());
        System.out.println("总录数："+page.getTotal());
        System.out.println("总页数："+page.getPages());
        System.out.println("上一页："+(page.hasPrevious()?page.getCurrent()-1:1));
        System.out.println("下一页："+(page.hasNext()?page.getCurrent()+1:page.getPages()));*/
        return "job/list";
    }

    @RequestMapping("/toadd")
    public String toadd() {
        return "job/add";
    }

    @RequestMapping("/add")
    public String add(JobInf jobInf) {

        jobInfService.save(jobInf);
        return "redirect:/job/list";
    }

    @RequestMapping("/delete")
    public String del(Integer id) {
        jobInfService.removeById(id);
        return "redirect:/dept/list";
    }


    @RequestMapping("/toedit")
    public String toedit(Integer id, Model model) {
        JobInf jobInf = jobInfService.getById(id);
        model.addAttribute("dept", jobInf);
        return "job/edit";
    }

    @RequestMapping("/edit")
    public String edit(JobInf jobInf) {
        jobInfService.saveOrUpdate(jobInf);
        return "redirect:/job/list";
    }



}

