package com.ly.bigdata.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailUtils {
    public static void sendMail(JavaMailSender mailSender,
                                String subject,
                                String text,
                                String from,
                                String[] to){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(from);
        message.setTo(to);
        mailSender.send(message);
    }
}
