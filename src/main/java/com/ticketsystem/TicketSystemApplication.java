package com.ticketsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 车站售票系统启动类
 */
@SpringBootApplication
@MapperScan("com.ticketsystem.mapper")
@ServletComponentScan
public class TicketSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketSystemApplication.class, args);
        System.out.println("==========================================");
        System.out.println("车站售票系统启动成功！");
        System.out.println("访问地址: http://localhost:8080");
        System.out.println("==========================================");
    }
}

