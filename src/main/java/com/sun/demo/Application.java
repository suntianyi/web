package com.sun.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @auther zhsun5@iflytek.com
 * @date 2017/12/29
 */
@SpringBootApplication
@MapperScan(basePackages = "com.sun.demo.mybatis.mapper")
public class Application {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }
}
