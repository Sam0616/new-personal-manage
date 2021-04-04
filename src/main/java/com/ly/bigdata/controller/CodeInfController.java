package com.ly.bigdata.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ly.bigdata.po.CodeInf;
import com.ly.bigdata.po.UserInf;
import com.ly.bigdata.service.CodeInfService;
import com.ly.bigdata.service.SendMailService;
import com.ly.bigdata.service.UserInfService;
import com.ly.bigdata.utils.MailUtils;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.undertow.UndertowWebServer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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
public class CodeInfController {


    @Autowired
    private CodeInfService codeInfService;
    @Autowired
    private UserInfService userInfService;
    @Autowired
    private SendMailService mailService;

    @ResponseBody
    @RequestMapping("/toregistCode")
    public String toregistCode(String registCode) {
        //验证注册码is  right？
        CodeInf codeInf = codeInfService.getOne(new QueryWrapper<CodeInf>().eq("code", registCode));


        if (codeInf != null) {
            //验证注册码是否已经审核过
            if (codeInf.getStatus() == 1) {
                return "";
            } else {
                //验证注册码没有审核过
                return "注册码未审核,请重新输入！";
            }

        } else {
            return "注册码不正确,请重新输入！";
        }

    }

    @RequestMapping("/toregist")
    public String toregist(String registCode, Model model) {

        //删除已经使用的注册码
        QueryWrapper<CodeInf> codeInfQueryWrapper = new QueryWrapper<>();
        codeInfQueryWrapper.eq("code", registCode);
        boolean b = codeInfService.remove(codeInfQueryWrapper);
        System.err.println("b=============" + b);

        return "regist";
    }


    @RequestMapping("/check_Register_loginname")
    @ResponseBody
    public String check_Register_loginname(String loginname) {
        QueryWrapper<UserInf> Wrapper = new QueryWrapper<>();
        Wrapper.eq("loginname", loginname);
        UserInf userInf = userInfService.getOne(Wrapper);
        if (userInf != null) {
            return "登录名已存在，请更换！";
        } else {
            return "";
        }
    }


    @RequestMapping("/register")
    public String addUser(UserInf userInf, Model model) {

        userInf.setStatusId(2);
        userInf.setCreatedate(new Date());
        System.out.println(userInf);
        // md5加密
        String pwd = DigestUtils.md5DigestAsHex(userInf.getPassword().getBytes());
        userInf.setPassword(pwd);
        userInfService.save(userInf);
        // 发送邮件给管理员
        mailService.send("新用户注册","新用户来注册啦，麻烦审核~","3136667558@qq.com",new String[]{"454374832@qq.com"});
        model.addAttribute("msg", "true");
        return "loginForm";
    }


    @RequestMapping("/check_Register_email")
    @ResponseBody
    public String check_Register_email(String email) {
        QueryWrapper<UserInf> wrapper = new QueryWrapper<UserInf>();
        wrapper.eq("email", email);
        UserInf userInf = userInfService.getOne(wrapper);
        if (userInf != null) {
            return "邮箱已存在，请更换！";
        } else {
            return "";
        }
    }
}


