package com.ly.bigdata.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ly.bigdata.po.CodeInf;
import com.ly.bigdata.po.UserInf;
import com.ly.bigdata.po.UservisitInf;
import com.ly.bigdata.service.CodeInfService;
import com.ly.bigdata.service.UservisitInfService;
import com.ly.bigdata.utils.GenCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller

public class IndexController {

    @Autowired
    private CodeInfService codeInfService;
    @Autowired
    private UservisitInfService uservisitInfService;
    @Autowired
    private StringRedisTemplate redisTemplate;


    @RequestMapping("/")
    public String toLogin() {
        return "loginForm";
    }

    @RequestMapping("/toindex")
    public String toIndex() {
        return "index";
    }

    @RequestMapping("/welcome")
    public String toWelcome() {
        return "welcome";
    }


    @RequestMapping("/registCode")
    public String registCode() {
        return "registCode";
    }

    @RequestMapping("/repassword")
    public String repassword() {
        return "repasswordPage";
    }


    @RequestMapping("/exit")
    public String logout(HttpSession session) throws JsonProcessingException {
        // 从session获取UserVisitInf
        UservisitInf USER_VISIT = (UservisitInf) session.getAttribute("USER_VISIT");
        if (USER_VISIT != null) {
            USER_VISIT.setExitTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            uservisitInfService.saveOrUpdate(USER_VISIT);
        }

        //更新redis中登出时间的值
        if (USER_VISIT != null) {
            List<String> totalList = redisTemplate.opsForList().range("users", 0, -1);
            redisTemplate.delete("users");
            for (int i = 0; i < totalList.size(); i++) {
                try {
                    UservisitInf uservisit = new ObjectMapper().readValue(totalList.get(i), UservisitInf.class);
                    System.err.println(USER_VISIT.getId() == uservisit.getId());
                    if (USER_VISIT.getId().equals(uservisit.getId())) {
                        USER_VISIT.setExitTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                        String s = new ObjectMapper().writeValueAsString(USER_VISIT);
                        redisTemplate.opsForList().rightPush("users", s);
                    } else {
                        redisTemplate.opsForList().rightPush("users", totalList.get(i));
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
        session.invalidate();
        return "loginForm";
    }


    @RequestMapping("/toCreateCode")
    @ResponseBody
    public Object toCreateCode(HttpSession session) {

        Integer time = (Integer) session.getAttribute("number");
        System.err.println("==================" + time);
        session.setAttribute("number", time - 1);
        Integer time2 = (Integer) session.getAttribute("number");
        if (time2 < 0) {
            //不能再获取注册码
            CodeInf codeInf = new CodeInf();
            codeInf.setCode("您已经没有机会获取注册码！");
            return codeInf;
        } else {
            String code = GenCode.gen();
            CodeInf codeInf = new CodeInf();
            codeInf.setCode(code);
            codeInf.setStatus(1);
            codeInf.setCreatedate(new Date());
            codeInfService.save(codeInf);

            return codeInf;
        }
    }

    //记录登出时间，刷新一次index记录一次
    @ResponseBody
    @RequestMapping("/test")
    public String test(HttpSession session) throws JsonProcessingException {

        // 从session获取UserVisitInf
        UservisitInf USER_VISIT = (UservisitInf) session.getAttribute("USER_VISIT");
        if (USER_VISIT != null) {
            USER_VISIT.setExitTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            uservisitInfService.saveOrUpdate(USER_VISIT);
        }
        System.err.println("已记录登出时间!");

        //更新redis 数据库字段 离开时间 的值
        if (USER_VISIT != null) {
            List<String> totalList = redisTemplate.opsForList().range("users", 0, -1);
            redisTemplate.delete("users");
            for (int i = 0; i < totalList.size(); i++) {
                try {
                    UservisitInf uservisit = new ObjectMapper().readValue(totalList.get(i), UservisitInf.class);
                    if (USER_VISIT.getId().equals(uservisit.getId())) {
                        USER_VISIT.setExitTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                        String s = new ObjectMapper().writeValueAsString(USER_VISIT);
                        redisTemplate.opsForList().rightPush("users", s);
                    } else {
                        redisTemplate.opsForList().rightPush("users", totalList.get(i));
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
        return "ok";
    }
}
