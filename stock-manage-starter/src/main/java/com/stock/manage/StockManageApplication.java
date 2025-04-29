package com.stock.manage;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@MapperScan(basePackages = "com.stock.manage.mapper")
public class StockManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockManageApplication.class, args);
    }
} 