package com.stock.manage.vo;

import lombok.Data;

@Data
public class InventoryVO {
    private Long id;
    private Long productId;
    private Integer sellableQty;
    private Integer reservedQty;
    private Integer inboundQty;
    private String dataSource;
} 