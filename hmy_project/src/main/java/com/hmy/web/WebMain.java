package com.hmy.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.hmy.web.mapper")
public class WebMain
{
    public static void main(String[] args)
    {
        SpringApplication.run(WebMain.class,args);
    }
}
