package com.ly.bigdata.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ly.bigdata.po.UservisitInf;
import com.ly.bigdata.service.UservisitInfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 陈太康
 * @since 2021-03-23
 */
@Controller
@RequestMapping("/uservisit")
public class UservisitInfController {
    @Autowired
    private UservisitInfService uservisitInfService;
    @Autowired
    private StringRedisTemplate redisTemplate;


    @RequestMapping("/list")
    public String list(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                       @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                       Model model,
                       @RequestParam(value = "content", required = false) String content) throws JsonProcessingException {

        //List<String> users = redisTemplate.opsForList().range("users", (pageNo - 1) * pageSize, pageNo * pageSize - 1);
        List<UservisitInf> list = new ArrayList<>();
        long total = redisTemplate.opsForList().size("users");
        List<String> usersToal = redisTemplate.opsForList().range("users", 0, total);
        List<UservisitInf> targetList = new ArrayList<>();

        for (int i = 0; i < usersToal.size(); i++) {
            UservisitInf uservisitInf = new ObjectMapper().readValue(usersToal.get(i), UservisitInf.class);
            if (content != null && !"".equals(content)) {
                if (uservisitInf.getLoginname().contains(content) || uservisitInf.getBrowser().contains(content)) {
                    targetList.add(uservisitInf);
                }
            } else {
                targetList.add(uservisitInf);
            }
        }
        //增加分页功能
        try {
            for (int i = (pageNo - 1) * pageSize; i < pageNo * pageSize; i++) {
                list.add(targetList.get(i));
            }
        } catch (Exception e) {
            list.clear();
            for (int i = (pageNo - 1) * pageSize; i < targetList.size(); i++) {
                list.add(targetList.get(i));
            }
        }

        //redis数据库是空的时候取数据库
        if (targetList.isEmpty() || targetList.size() < 1) {
            System.out.println("=============取数据库===================");
            Page<UservisitInf> page = new Page<>(pageNo, pageSize);
            if (content != null && !"".equals(content)) {
                QueryWrapper<UservisitInf> wrapper = new QueryWrapper<>();
                wrapper.like("login_time", "%" + content + "%")
                        .or().like("exit_time", "%" + content + "%");
                uservisitInfService.page(page, wrapper);
            } else {
                uservisitInfService.page(page, null);
            }
            model.addAttribute("list", page.getRecords());
            model.addAttribute("pageNo", page.getCurrent());//当前页
            model.addAttribute("pageSize", page.getSize());////每页记录数
            model.addAttribute("count", page.getTotal());//总记录数

        } else {
            System.out.println("=============取redis数据==================");
            model.addAttribute("list", list);
            model.addAttribute("pageNo", pageNo);//当前页
            model.addAttribute("pageSize", pageSize);////每页记录数
            model.addAttribute("count", targetList.size());//总记录数
        }
        return "uservisit/list";
    }


    @RequestMapping("/delete")
    public String del(Integer id) throws JsonProcessingException {
        uservisitInfService.removeById(id);
        //删除redis的数据
        List<String> totallist = redisTemplate.opsForList().range("users", 0, -1);
        redisTemplate.delete("users");

        for (int i = 0; i < totallist.size(); i++) {
            UservisitInf uservisitInf = new ObjectMapper().readValue(totallist.get(i), UservisitInf.class);
            if (!uservisitInf.getId().equals( id)) {
                redisTemplate.opsForList().rightPush("users", totallist.get(i));
            }
        }

        return "redirect:/uservisit/list";
    }

}

