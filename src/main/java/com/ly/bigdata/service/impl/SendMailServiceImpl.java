package com.ly.bigdata.service.impl;

import com.ly.bigdata.service.SendMailService;
import com.ly.bigdata.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SendMailServiceImpl implements SendMailService {

    @Autowired
    private JavaMailSender javaMailSender;



    @Override
    @Async//开启异步任务
    public void send(String subject, String text, String from, String[] to) {
        MailUtils.sendMail(javaMailSender, subject, text, from, to);
    }
}
