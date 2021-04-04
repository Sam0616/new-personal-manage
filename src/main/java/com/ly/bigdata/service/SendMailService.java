package com.ly.bigdata.service;

public interface SendMailService {
     void send(String subject,String text,String from,String[] to);
}
