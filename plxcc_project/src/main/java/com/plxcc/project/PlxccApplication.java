package com.plxcc.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @PackgeName: com.plxcc.project
 * @ClassName: PlxccApplication
 * @Author: plxc
 * Date: 2020/8/6 18:13
 * project name: parent
 * @Version:
 * @Description:
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.plxcc")
public class PlxccApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlxccApplication.class,args);
    }
}
