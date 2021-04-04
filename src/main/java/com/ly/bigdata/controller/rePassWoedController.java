package com.ly.bigdata.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ly.bigdata.po.UserInf;
import com.ly.bigdata.service.UserInfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class rePassWoedController {
    @Autowired
    private UserInfService userInfService;

    @RequestMapping("/toFindPassword")
    public String toFindPassword(String loginname, String username, String user_input_verifyCode, HttpSession session, Model model) {


        String code = (String) session.getAttribute("code");

        if (code.equalsIgnoreCase(user_input_verifyCode)){
            //验证码正确
            QueryWrapper<UserInf> wrapper = new QueryWrapper<>();
            wrapper.eq("loginname",loginname).eq("username",username);
            UserInf userInf = userInfService.getOne(wrapper);
            if (userInf!=null){
                //查有此人
                System.err.println("登录名~~~~~~~~"+loginname);
                model.addAttribute("loginname",loginname);
                return "findPassword";
            }else {
                model.addAttribute("flag","查无此人");
                return "repasswordPage";
            }
        }else {
            //验证码不对
            model.addAttribute("flag","验证码不正确");
            return "repasswordPage";
        }
    }

    @RequestMapping("/rePassword")
    public String rePassword(String password,String loginname,Model model) {
        System.err.println("~~~~~~~~~~~~~~~~www~~~~~~" + password);
        System.err.println("~~~~~~~~~~~~~eee~~~~~~~~~" + loginname);
        UpdateWrapper<UserInf> wrapper = new UpdateWrapper<>();
        UpdateWrapper<UserInf> wrapper2 = new UpdateWrapper<>();
        //MD5加密
        String s = DigestUtils.md5DigestAsHex(password.getBytes());
        //新密码与旧密码相同的情况，，，，，待优化
        wrapper.eq("loginname", loginname).eq("password", s);
        UserInf userInf = userInfService.getOne(wrapper);
        if (userInf != null) {
            //新旧密码相同
            model.addAttribute("loginname", loginname);
            model.addAttribute("flag", "新旧密码相同!");
            return "findPassword";
        } else {
        wrapper2.eq("loginname", loginname).set("password", s);
        boolean b = userInfService.update(wrapper2);
        System.err.println("~~~~~~~~~~~~~~~~~~~~~" + b);
        if (b == true) {
            //修改密码完事了
            model.addAttribute("flag", "true");
            return "loginForm";
        } else {
            //没有修改成
            model.addAttribute("loginname", loginname);
            model.addAttribute("flag", "修改失败");
            return "findPassword";
        }
    }
    }
}