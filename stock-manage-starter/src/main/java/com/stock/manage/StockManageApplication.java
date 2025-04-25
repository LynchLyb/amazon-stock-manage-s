package com.stock.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans({
      @ComponentScan("com")
})
public class StockManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockManageApplication.class, args);
    }
} 