package com.ly.bigdata.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ly.bigdata.po.DeptInf;
import com.ly.bigdata.po.NoticeInf;
import com.ly.bigdata.po.UserInf;
import com.ly.bigdata.service.NoticeInfService;
import com.ly.bigdata.service.UserInfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-26
 */
@Controller
@RequestMapping("/notice")
public class NoticeInfController {
    @Autowired
    private NoticeInfService noticeInfService;
    @Autowired
    private UserInfService userInfService;

    @RequestMapping("/toadd")
    public String toadd() {
        return "notice/add";
    }

    @RequestMapping("/add")
    public String add(NoticeInf noticeInf) {
        noticeInf.setCreatedate(new Date());
        System.err.println("~~~~~~~~" + noticeInf);
        noticeInfService.save(noticeInf);
        return "redirect:/notice/list";
    }


    @RequestMapping("/list")
    public String list(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       Model model,
                       @RequestParam(value = "content", required = false) String content) {
        Page<NoticeInf> page = new Page<>(pageNo, pageSize);
        if (content != null && !content.equals("")) {
            QueryWrapper<NoticeInf> wrapper = new QueryWrapper<>();
            wrapper.like("title", "%" + content + "%")
                    .or().like("content", "%" + content + "%");
            noticeInfService.page(page, wrapper);
        } else {
            noticeInfService.page(page, null);
        }
        List<NoticeInf> list2 = new ArrayList<>();
        List<NoticeInf> list = page.getRecords();
        list.forEach(item -> {
            Integer id = item.getUserId();
            UserInf userInf = userInfService.getById(id);
            String username = userInf.getUsername();

            item.setUserName(username);
            list2.add(item);

        });


        model.addAttribute("list", list2);
        model.addAttribute("pageNo", page.getCurrent());//当前页
        model.addAttribute("pageSize", page.getSize());////每页记录数
        model.addAttribute("count", page.getTotal());//总记录数

        return "notice/list";
    }


    @RequestMapping("/delete")
    public String del(Integer id) {
        noticeInfService.removeById(id);
        return "redirect:/notice/list";
    }


    @RequestMapping("/toedit")
    public String toedit(Integer id, Model model) {
        NoticeInf noticeInf = noticeInfService.getById(id);
        Integer userId = noticeInf.getUserId();
        UserInf userInf = userInfService.getById(userId);
        noticeInf.setUserName(userInf.getUsername());
        model.addAttribute("notice", noticeInf);
        return "notice/edit";
    }

    @RequestMapping("/edit")
    public String edit(NoticeInf noticeInf) {
        noticeInfService.saveOrUpdate(noticeInf);
        return "redirect:/notice/list";
    }


}

