package com.ly.bigdata.controller;

import com.ly.bigdata.service.SendMailService;
import com.ly.bigdata.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestSendMailController {
    @Autowired
    private SendMailService sendMailService;
    @Autowired
    private JavaMailSender javaMailSender;

    @RequestMapping("/send")
    public String send(){
        String[] to={"454374832@qq.com"};
        MailUtils.sendMail(javaMailSender,"注册用户1","请审核","3136667558@qq.com",to);
        return "send mail ok";
    }


    @RequestMapping("/send2")
    public String send2(){
        String[] to={"454374832@qq.com"};
        sendMailService.send("注册用户1","请审核","3136667558@qq.com",to);
        return "send mail ok2";
    }

}
