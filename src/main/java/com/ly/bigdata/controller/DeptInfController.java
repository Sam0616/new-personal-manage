package com.ly.bigdata.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.DeptInf;
import com.ly.bigdata.po.UserInf;
import com.ly.bigdata.service.DeptInfService;
import com.ly.bigdata.vo.DeptNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-23
 */
@Controller
@RequestMapping("/dept")
public class DeptInfController {
    @Autowired
    private DeptInfService deptInfService;

    @RequestMapping("/list")
    public String list(@RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
                       @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                       Model model,
                       @RequestParam(value = "content",required = false) String content){
        Page<DeptInf> page=new Page<>(pageNo,pageSize);
        if (content!=null&&!content.equals("")){
            QueryWrapper<DeptInf> wrapper=new QueryWrapper<>();
            wrapper.like("name","%"+content+"%")
                    .or().like("remark","%"+content+"%");
            deptInfService.page(page,wrapper);
        }else {
            deptInfService.page(page,null);
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
        return "dept/list";
    }
    @RequestMapping("/toadd")
    public String toadd() {
        return "dept/add";
    }

    @RequestMapping("/add")
    public String add(DeptInf deptInf) {

        deptInfService.save(deptInf);
        return "redirect:/dept/list";
    }


    @RequestMapping("/delete")
    public String del(Integer id) {
        deptInfService.removeById(id);
        return "redirect:/dept/list";
    }

    @RequestMapping("/toedit")
    public String toedit(Integer id, Model model) {
        DeptInf byId = deptInfService.getById(id);
        model.addAttribute("dept", byId);
        return "dept/edit";
    }

    @RequestMapping("/edit")
    public String edit(DeptInf deptInf) {
        deptInfService.saveOrUpdate(deptInf);
        return "redirect:/edit/list";
    }






    @RequestMapping("/getDeptNum")
    @ResponseBody
    public Object getDeptNum() {
        List<DeptNum> list =deptInfService.getDeptNumbers();
        list.forEach(d-> System.out.println(d));
        return list;
    }


}

