package com.stock.manage.vo;

import lombok.Data;
import java.util.List;

@Data
public class ProductInventorySalesVO {
    private ProductVO product;
    private InventoryVO inventory;
    private List<DailySalesRecordVO> dailySales;
    
    @Data
    public static class DailySalesRecordVO {
        private Integer quantity;
        private String recordDate;
        private String dataSource;
    }
} 