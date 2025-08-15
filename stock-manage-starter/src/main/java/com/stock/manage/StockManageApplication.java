package com.stock.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.stock.manage.mapper")
public class StockManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockManageApplication.class, args);
    }
} 