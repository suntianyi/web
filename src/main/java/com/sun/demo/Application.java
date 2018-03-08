package com.sun.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author zhsun5@iflytek.com
 * @date 2017/12/29
 */
@SpringBootApplication
@MapperScan(basePackages = "com.sun.demo.mybatis.mapper")
public class Application {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        String[] activeProfiles = ctx.getEnvironment().getActiveProfiles();
        for (String profile : activeProfiles) {
            System.out.println("Spring Boot 使用profile为: application-"+ profile+".yml");
        }
    }
}
