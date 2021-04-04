package com.ly.bigdata;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@MapperScan("com.ly.bigdata.mapper")
@SpringBootApplication
@EnableAsync  //在相应的耗时方法上加上@Async,就能开启异步，本项目应用于发邮件
public class NewPersonalManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewPersonalManageApplication.class, args);
    }

}
