package com.stock.manage.vo;

import lombok.Data;
import java.util.List;

@Data
public class ProductMonthlySalesVO {
    private ProductVO product;
    private InventoryVO inventory;
    private List<DailySalesVO> dailySales;
    
    @Data
    public static class DailySalesVO {
        private String date;        // 日期，格式：YYYY-MM-DD
        private Integer quantity;   // 销量
        private String dataSource;  // 数据来源
    }
} 