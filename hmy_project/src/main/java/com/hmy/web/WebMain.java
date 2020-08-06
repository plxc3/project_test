package com.hmy.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.hmy.web.mapper")
@ComponentScan(basePackages = {"com.plxcc","com.hmy"})
public class WebMain
{
    public static void main(String[] args)
    {
        SpringApplication.run(WebMain.class,args);
    }
}
