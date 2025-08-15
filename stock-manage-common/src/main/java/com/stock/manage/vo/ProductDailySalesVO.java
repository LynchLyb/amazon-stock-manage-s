package com.stock.manage.vo;

import lombok.Data;

@Data
public class ProductDailySalesVO {

    private ProductVO product;
    private InventoryVO inventory;
    private DailySalesVO dailySale;

    @Data
    public static class DailySalesVO {
        private String date;        // 日期，格式：YYYY-MM-DD
        private Integer quantity;   // 销量
        private String dataSource;  // 数据来源
    }

}
