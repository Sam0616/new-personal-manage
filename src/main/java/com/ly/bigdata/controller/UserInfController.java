package com.ly.bigdata.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.StatusInf;
import com.ly.bigdata.po.UserInf;
import com.ly.bigdata.service.StatusInfService;
import com.ly.bigdata.service.UserInfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-22
 */
@Controller
@RequestMapping("/user")
public class UserInfController {

    @Autowired
    private UserInfService userInfService;
    @Autowired
    private StatusInfService statusInfService;


    @RequestMapping("/list")
    public String list(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       Model model,
                       @RequestParam(value = "content", required = false) String content) {
        Page<UserInf> page = new Page<>(pageNo, pageSize);
        if (content != null && !content.equals("")) {
            QueryWrapper<UserInf> wrapper = new QueryWrapper<>();
            wrapper.like("loginname", "%" + content + "%")
                    .or().like("username", "%" + content + "%");
            userInfService.page(page, wrapper);
        } else {
            userInfService.page(page, null);
        }
        model.addAttribute("list", page.getRecords());
        model.addAttribute("pageNo", page.getCurrent());//当前页
        model.addAttribute("pageSize", page.getSize());////每页记录数
        model.addAttribute("count", page.getTotal());//总记录数
         /*System.out.println("当前页："+page.getCurrent());
        System.out.println("每页记录数："+page.getSize());
        System.out.println("总录数："+page.getTotal());
        System.out.println("总页数："+page.getPages());
        System.out.println("上一页："+(page.hasPrevious()?page.getCurrent()-1:1));
        System.out.println("下一页："+(page.hasNext()?page.getCurrent()+1:page.getPages()));*/
        return "user/list";
    }

    @RequestMapping("/toadd")
    public String toadd() {
        return "user/add";
    }

    @RequestMapping("/add")
    public String add(UserInf userInf) {
        userInf.setStatusId(0);
        userInf.setCreatedate(new Date());

        // md5加密
        String s = DigestUtils.md5DigestAsHex(userInf.getPassword().getBytes());
        userInf.setPassword(s);
        userInfService.save(userInf);
        return "redirect:/user/list";
    }

    @RequestMapping("/delete")
    public String del(Integer id) {
        userInfService.removeById(id);
        return "redirect:/user/list";
    }


    @RequestMapping("/toedit")
    public String toedit(Integer id, Model model) {
        UserInf userInf = userInfService.getById(id);
        List<StatusInf> list = statusInfService.list();
        model.addAttribute("status", list);
        model.addAttribute("user", userInf);
        return "user/edit";
    }

    @RequestMapping("/edit")
    public String edit(UserInf userInf) {
       // System.err.println("~~~~~~~~~~~~~~~~~~~~~~~~"+userInf);
        Integer id = userInf.getId();
        UserInf byId = userInfService.getById(id);
        System.err.println(byId);
        Date createdate = byId.getCreatedate();
        userInf.setCreatedate(createdate);
        userInfService.saveOrUpdate(userInf);
        return "redirect:/user/list";
    }

    @RequestMapping("/topedit")
    public String getUserById(Integer id, Model model) {
        System.err.println("topedit~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        UserInf userInf = userInfService.getById(id);
        model.addAttribute("user", userInf);
        return "user/pedit";
    }

    @RequestMapping("/pedit")
    public String peditUser(UserInf userInf) {
        System.err.println(userInf);
        Integer id = userInf.getId();
        UserInf byId = userInfService.getById(id);
        System.err.println(byId);
        Date createdate = byId.getCreatedate();
        userInf.setCreatedate(createdate);
        // md5加密
        String s = DigestUtils.md5DigestAsHex(userInf.getPassword().getBytes());
        userInf.setPassword(s);
        userInfService.saveOrUpdate(userInf);
        return "redirect:/user/list";
    }








}

